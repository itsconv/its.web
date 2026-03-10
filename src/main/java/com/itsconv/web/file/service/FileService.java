package com.itsconv.web.file.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.itsconv.web.board.domain.Board;
import com.itsconv.web.board.repository.BoardRepository;
import com.itsconv.web.board.service.dto.command.BoardSlotCommand;
import com.itsconv.web.common.exception.BusinessException;
import com.itsconv.web.common.exception.ErrorCode;
import com.itsconv.web.common.util.DateUtil;
import com.itsconv.web.file.controller.dto.response.FileEditorResponse;
import com.itsconv.web.file.domain.FileDetail;
import com.itsconv.web.file.domain.FileStatus;
import com.itsconv.web.file.repository.FileDetailRepository;
import com.itsconv.web.file.repository.FileRepository;
import com.itsconv.web.file.service.dto.command.FileConnectBoardCommand;
import com.itsconv.web.file.service.dto.command.FileDetailCommand;
import com.itsconv.web.file.service.dto.command.FileUploadCommand;
import com.itsconv.web.view.admin.dto.FileAttachView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final FileDetailRepository fileDetailRepository;
    private final BoardRepository boardRepository;

    private final Environment env;
    
    @Transactional(readOnly = true)
    public com.itsconv.web.file.domain.File findFileById(Long id) {
        return fileRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<FileAttachView> findAttachesByBoardId(Long boardId) {
        return fileDetailRepository.findAttachsByBoardId(boardId);
    }

    @Transactional(readOnly = true)
    public List<Long> findFileIdsByBoardIds(List<Long> boardIds) {
        return fileDetailRepository.findFileIdsByBoardIds(boardIds);
    }

    /**
     * 에디터에서 파일첨부.
     * Board와의 연관성에 따라 FileStatus 핸들링
     * TEMP : 게시판 저장 전 에디터 파일첨부
     * USED : 게시판 외 에디터 파일첨부
     */
    @Transactional
    public FileEditorResponse uploadEditorFile(MultipartFile file, FileStatus status) {
        com.itsconv.web.file.domain.File savedFile = storeOneFileMeta(file);

        String viewUrl = env.getProperty("app.file.view-dir") 
            + String.valueOf(savedFile.getId());

        // 바로 확정인 경우
        if ("USED".equals(status.toString())) {
            return new FileEditorResponse(viewUrl, savedFile.getId());
        }

        // 게시판 에디터 임시 저장
        FileDetail detail = setDetailTemp(savedFile);

        return new FileEditorResponse(viewUrl, detail.getId());
    }

    /**
     * 임시저장된 에디터 첨부파일 확정 및 게시판 첨부파일 저장
     * @param slots: 첨부파일별 썸네일 여부 순서보장을 위한 매핑용 DTO
     * @param command: 게시판과 파일, 썸네일 여부 매핑 DTO
     */
    @Transactional
    public void uploadFileFromBoard(List<BoardSlotCommand> slots, FileConnectBoardCommand command) {
        if (command.boardId() == null) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }
        Board board = boardRepository.findById(command.boardId())
            .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_BAD_REQUEST));

        confirmBoardFiles(slots, command.thumbnailOrder(), board);

        confirmEditorFiles(board, command.detailIds());
    }

    @Transactional
    public void deleteFile(Long id) {
        com.itsconv.web.file.domain.File file = fileRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_BAD_REQUEST));

        fileRepository.delete(file);

        if(!deleteStoredFile(file)) {
            throw new BusinessException(ErrorCode.FILE_DELETE_FAILED);
        }
    }

    @Transactional
    public void deleteFiles(List<Long> ids) {
        List<com.itsconv.web.file.domain.File> files = fileRepository.findAllById(ids);

        if (files.isEmpty()) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        fileRepository.deleteAll(files);
        
        for (com.itsconv.web.file.domain.File file : files) {
            if(!deleteStoredFile(file)) {
                throw new BusinessException(ErrorCode.FILE_DELETE_FAILED);
            }
        }
    }

    private boolean deleteStoredFile(com.itsconv.web.file.domain.File file) {
        String path = file.getPath();
        String uuid = file.getUuid();

        File targetFile = new File(path, uuid);

        if (!targetFile.exists()) {
            throw new BusinessException(ErrorCode.FILE_NOT_FOUND);
        }

        return targetFile.delete();
    }

    private void confirmBoardFiles(List<BoardSlotCommand> slots, Integer thumbOrder, Board board) {
        if (slots == null || slots.isEmpty()) return;

        List<FileDetail> details = new ArrayList<>();
        
        for (BoardSlotCommand slot : slots) {
            com.itsconv.web.file.domain.File saved = storeOneFileMeta(slot.file());

            String thumbYn = (thumbOrder != null && thumbOrder.equals(slot.slotNo())) ? "Y" : "N";

            FileDetailCommand detailCommand = new FileDetailCommand(
                saved, board, thumbYn, slot.slotNo(), FileStatus.USED.toString());

            FileDetail detail = new FileDetail();
            
            detail.saveDetail(detailCommand);
            
            details.add(detail);            
        }
            
        fileDetailRepository.saveAll(details);
    }

    private void confirmEditorFiles(Board board, List<Long> detailIds) {
        // 에디터 파일 확정
        if (detailIds == null || detailIds.isEmpty()) return;

        List<FileDetail> details = new ArrayList<>();
        
        for (Long detailId : detailIds) {
            FileDetail detail = fileDetailRepository.findById(detailId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_BAD_REQUEST));
            
            detail.updateStatus(board, FileStatus.USED.toString());
            
            details.add(detail);
        }

        fileDetailRepository.saveAll(details);
    }

    private FileDetail setDetailTemp(com.itsconv.web.file.domain.File entity) {        
        FileDetailCommand command = new FileDetailCommand(
            entity, null, "N", 0, FileStatus.TEMP.toString());

        FileDetail detail = new FileDetail();

        detail.saveDetail(command);

        return fileDetailRepository.save(detail);
    }

    private com.itsconv.web.file.domain.File storeOneFileMeta(MultipartFile attachFile) {
        String prefix = env.getProperty("app.file.base-dir");
		String yearDir = DateUtil.toString(new Date(), "yyyy");
		String monthDir = DateUtil.toString(new Date(), "MM");
		
		String filePath = prefix + yearDir + "/" + monthDir + "/";

        log.info("Check upload path : {}", filePath);

        String originName = attachFile.getOriginalFilename();
            String ext = originName.substring(originName.lastIndexOf(".") + 1); //확장자

            String uuid = UUID.randomUUID() + "." + ext;

            File destination = new File(filePath);

            // 존재하지않으면 생성
            if (!destination.exists()) destination.mkdirs();

            File targetFile = new File(filePath, uuid);

            com.itsconv.web.file.domain.File entity = new com.itsconv.web.file.domain.File();

            try {
                // 파일전송
                attachFile.transferTo(targetFile);
    
                //File 엔티티 저장
                FileUploadCommand command = new FileUploadCommand(attachFile.getSize(), originName, filePath, uuid);
    
                entity.saveFile(command);
            } catch (Exception e) {
                log.error("Failed to upload files - filename = {}", attachFile.getOriginalFilename(), e);
                throw new BusinessException(ErrorCode.FILE_UPLOAD_FAILED);
            } 

        return fileRepository.save(entity);
    }
}
