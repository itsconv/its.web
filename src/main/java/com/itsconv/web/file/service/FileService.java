package com.itsconv.web.file.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
import com.itsconv.web.file.domain.FileBoard;
import com.itsconv.web.file.domain.FileStatus;
import com.itsconv.web.file.repository.FileBoardRepository;
import com.itsconv.web.file.repository.FileRepository;
import com.itsconv.web.file.service.dto.command.FileBoardCommand;
import com.itsconv.web.file.service.dto.command.FileConnectBoardCommand;
import com.itsconv.web.file.service.dto.command.FileRemoveCommand;
import com.itsconv.web.file.service.dto.command.FileUpdateCommand;
import com.itsconv.web.file.service.dto.command.FileUploadCommand;
import com.itsconv.web.file.service.dto.view.BoardThumbnailView;
import com.itsconv.web.view.admin.dto.FileAttachView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final FileBoardRepository fileBoardRepository;
    private final BoardRepository boardRepository;

    private final Environment env;
    
    @Transactional(readOnly = true)
    public com.itsconv.web.file.domain.File findFileById(Long id) {
        return fileRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<FileAttachView> findAttachesByBoardId(Long boardId) {
        return fileBoardRepository.findAttachsByBoardId(boardId);
    }

    @Transactional(readOnly = true)
    public List<Long> findFileIdsByBoardIds(List<Long> boardIds) {
        return fileBoardRepository.findFileIdsByBoardIds(boardIds);
    }

    @Transactional(readOnly = true)
    public List<BoardThumbnailView> findThumbnailFileIdsByBoardIds(List<Long> boardIds) {
        if (boardIds == null || boardIds.isEmpty()) {
            return List.of();
        }

        return fileBoardRepository.findThumbnailFileIdsByBoardIds(boardIds);
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
        FileBoard detail = setDetailTemp(savedFile);

        return new FileEditorResponse(viewUrl, detail.getMappedId());
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

    // Board 삭제 후 연관 파일 제거
    @Transactional
    public void deleteFilesAfterRemoveBoards(List<Long> ids) {
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

    // 첨부된 파일만 제거
    @Transactional
    public void removeAttachment(FileRemoveCommand command) {
        com.itsconv.web.file.domain.File targetFile = fileBoardRepository.findFileByMappedId(command);

        if (targetFile == null) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        // 매핑 먼저 제거
        fileBoardRepository.deleteById(command.mappedId());

        // 파일 메타 제거
        fileRepository.delete(targetFile);

        if(!deleteStoredFile(targetFile)) {
            throw new BusinessException(ErrorCode.FILE_DELETE_FAILED);
        }
    }

    /**
     * 게시판 업데이트
     * 에디터 첨부파일 수정 시, 매핑 테이블 상태값 변경
     * 게시판 첨부파일 수정 시, 기존 파일 제거 확정
     * 신규 첨부파일 등록
     */
    @Transactional
    public void updateBoardFiles(Board board, List<BoardSlotCommand> files, FileUpdateCommand command) {
        removeScheduledAttachments(command.boardId(), command.removeMappedIds());
        markRemovedEditorFilesTemp(board.getId(), command.removeEditorDetailIds());

        confirmBoardFiles(files, command.thumbnailOrder(), board);

        confirmEditorFiles(board, command.detailIds());
    }

    // 에디터에서 첨부된 미아 파일 및 수정시 삭제된 임시상태의 파일 제거
    @Transactional
    public int purgeExpiredTempEditorFiles(LocalDateTime cutoff) {
        List<FileBoard> expiredFiles = fileBoardRepository.findExpiredTempFiles(
            FileStatus.TEMP.toString(),
            cutoff
        );

        int removedCount = 0;

        for (FileBoard detail : expiredFiles) {
            com.itsconv.web.file.domain.File file = detail.getFile();

            fileBoardRepository.delete(detail);
            fileRepository.delete(file);
            deleteStoredFileQuietly(file);

            removedCount++;
        }

        return removedCount;
    }

    // 게시판에 첨부된 파일 삭제
    private void removeScheduledAttachments(Long boardId, List<Long> removeMappedIds) {
        if (removeMappedIds == null || removeMappedIds.isEmpty()) return;

        for (Long mappedId : removeMappedIds) {
            removeAttachment(FileRemoveCommand.from(boardId, mappedId));
        }
    }

    // 에디터 첨부파일은 상태값만 변경. (실제 삭제는 스케줄러)
    private void markRemovedEditorFilesTemp(Long boardId, List<Long> removeEditorDetailIds) {
        if (removeEditorDetailIds == null || removeEditorDetailIds.isEmpty()) return;

        List<FileBoard> details = fileBoardRepository.findAllByMappedIds(removeEditorDetailIds);

        if (details.size() != removeEditorDetailIds.size()) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        for (FileBoard detail : details) {
            boolean belongsToBoard = detail.getBoard() != null && boardId.equals(detail.getBoard().getId());
            boolean isTempUpload = detail.getBoard() == null;
            boolean isEditorFile = detail.getSortOrder() != null && detail.getSortOrder() == 0;

            if (!isEditorFile || (!belongsToBoard && !isTempUpload)) {
                throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
            }

            detail.markEditorTemp();
        }

        fileBoardRepository.saveAll(details);
    }

    @Transactional
    public String copyEditorFilesForBoard(Long sourceBoardId, String sourceContents, Board targetBoard) {
        List<FileBoard> sourceEditorFiles = fileBoardRepository.findUsedEditorFilesByBoardId(
            sourceBoardId,
            FileStatus.USED.toString()
        );

        if (sourceEditorFiles.isEmpty()) {
            return sourceContents;
        }

        String copiedContents = sourceContents;

        for (FileBoard sourceDetail : sourceEditorFiles) {
            com.itsconv.web.file.domain.File copiedFile = copyStoredFile(sourceDetail.getFile());
            createCopiedEditorFileBoard(copiedFile, targetBoard);

            String oldViewUrl = env.getProperty("app.file.view-dir") + sourceDetail.getFile().getId();
            String newViewUrl = env.getProperty("app.file.view-dir") + copiedFile.getId();

            copiedContents = copiedContents.replace(oldViewUrl, newViewUrl);
        }

        return copiedContents;
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

    private void deleteStoredFileQuietly(com.itsconv.web.file.domain.File file) {
        String path = file.getPath();
        String uuid = file.getUuid();
        File targetFile = new File(path, uuid);

        if (!targetFile.exists()) {
            log.warn("Skip deleting missing temp file. fileId={}", file.getId());
            return;
        }

        if (!targetFile.delete()) {
            log.warn("Failed to delete expired temp file from storage. fileId={}", file.getId());
        }
    }

    private void confirmBoardFiles(List<BoardSlotCommand> slots, Integer thumbOrder, Board board) {
        if (slots == null || slots.isEmpty()) return;

        List<FileBoard> details = new ArrayList<>();
        
        for (BoardSlotCommand slot : slots) {
            com.itsconv.web.file.domain.File saved = storeOneFileMeta(slot.file());

            String thumbYn = (thumbOrder != null && thumbOrder.equals(slot.slotNo())) ? "Y" : "N";

            FileBoardCommand detailCommand = new FileBoardCommand(
                saved, board, thumbYn, slot.slotNo(), FileStatus.USED.toString());

            FileBoard detail = new FileBoard();
            
            detail.saveFileBoard(detailCommand);
            
            details.add(detail);            
        }
            
        fileBoardRepository.saveAll(details);
    }

    private void confirmEditorFiles(Board board, List<Long> detailIds) {
        // 에디터 파일 확정
        if (detailIds == null || detailIds.isEmpty()) return;

        List<FileBoard> details = new ArrayList<>();
        
        for (Long detailId : detailIds) {
            FileBoard detail = fileBoardRepository.findById(detailId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_BAD_REQUEST));
            
            detail.updateStatus(board, FileStatus.USED.toString());
            
            details.add(detail);
        }

        fileBoardRepository.saveAll(details);
    }

    private FileBoard setDetailTemp(com.itsconv.web.file.domain.File entity) {        
        FileBoardCommand command = new FileBoardCommand(
            entity, null, "N", 0, FileStatus.TEMP.toString());

        FileBoard detail = new FileBoard();

        detail.saveFileBoard(command);

        return fileBoardRepository.save(detail);
    }

    private FileBoard createCopiedEditorFileBoard(com.itsconv.web.file.domain.File file, Board board) {
        FileBoardCommand command = new FileBoardCommand(
            file, board, "N", 0, FileStatus.USED.toString()
        );

        FileBoard detail = new FileBoard();
        detail.saveFileBoard(command);

        return fileBoardRepository.save(detail);
    }

    private com.itsconv.web.file.domain.File copyStoredFile(com.itsconv.web.file.domain.File source) {
        String prefix = env.getProperty("app.file.base-dir");
        String yearDir = DateUtil.toString(new Date(), "yyyy");
        String monthDir = DateUtil.toString(new Date(), "MM");
        String filePath = prefix + yearDir + "/" + monthDir + "/";

        String ext = extractExtension(source);
        String uuid = UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);

        File destination = new File(filePath);
        if (!destination.exists()) destination.mkdirs();

        Path sourcePath = Paths.get(source.getPath(), source.getUuid());
        Path targetPath = Paths.get(filePath, uuid);

        try {
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Failed to copy stored file - fileId = {}", source.getId(), e);
            throw new BusinessException(ErrorCode.FILE_UPLOAD_FAILED);
        }

        return fileRepository.save(
            com.itsconv.web.file.domain.File.create(
                filePath,
                uuid,
                source.getOriginName(),
                source.getSize()
            )
        );
    }

    private String extractExtension(com.itsconv.web.file.domain.File source) {
        String originName = source.getOriginName();

        if (originName != null && originName.contains(".")) {
            return originName.substring(originName.lastIndexOf(".") + 1);
        }

        String uuid = source.getUuid();
        if (uuid != null && uuid.contains(".")) {
            return uuid.substring(uuid.lastIndexOf(".") + 1);
        }

        return "";
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
