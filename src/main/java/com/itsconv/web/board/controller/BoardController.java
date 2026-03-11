package com.itsconv.web.board.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itsconv.web.board.controller.dto.request.BoardCopyRequest;
import com.itsconv.web.board.controller.dto.request.BoardCreateRequest;
import com.itsconv.web.board.controller.dto.request.BoardModifyRequest;
import com.itsconv.web.board.controller.dto.request.BoardMoveRequest;
import com.itsconv.web.board.controller.dto.request.BoardOrderRequest;
import com.itsconv.web.board.service.BoardService;
import com.itsconv.web.board.service.dto.command.BoardSlotCommand;
import com.itsconv.web.common.response.ApiResponse;
import com.itsconv.web.security.service.UserPrincipal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bbs")
public class BoardController {
    private final BoardService boardService;

    @PutMapping("/move")
    public ResponseEntity<ApiResponse<Void>> modifyBoardType(
        @RequestBody BoardMoveRequest request, @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        boardService.move(request, userPrincipal);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/copy")
    public ResponseEntity<ApiResponse<Void>> createdCopied(
        @RequestBody BoardCopyRequest request, @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        boardService.copy(request, userPrincipal);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> removeBoard(
        @RequestBody List<Long> targetList) {
        boardService.deleteList(targetList);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> removeBoard(@PathVariable Long id) {
        boardService.delete(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PutMapping("/order")
    public ResponseEntity<ApiResponse<Void>> modifyOrder(
        @RequestBody List<BoardOrderRequest> list, @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        boardService.updateOrder(list, userPrincipal);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping(value="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Void>> createBoard(
        @Valid @RequestPart("request") BoardCreateRequest request,
        @RequestPart(value = "file1", required = false) MultipartFile file1,
        @RequestPart(value = "file2", required = false) MultipartFile file2,
        @RequestPart(value = "file3", required = false) MultipartFile file3,
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        List<BoardSlotCommand> files = List.of(
            new BoardSlotCommand(1, file1),
            new BoardSlotCommand(2, file2),
            new BoardSlotCommand(3, file3)
        ).stream().filter(m -> m.file() != null && !m.file().isEmpty()).toList();

        boardService.registerBoard(request, files, userPrincipal);

        return ResponseEntity.ok(ApiResponse.success());
    }
    
    @PutMapping(value="/{boardId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Void>> modifyBoard(
        @Valid @RequestPart("request") BoardModifyRequest request,
        @PathVariable("boardId") Long boardId,
        @RequestPart(value = "file1", required = false) MultipartFile file1,
        @RequestPart(value = "file2", required = false) MultipartFile file2,
        @RequestPart(value = "file3", required = false) MultipartFile file3,
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        List<BoardSlotCommand> files = List.of(
            new BoardSlotCommand(1, file1),
            new BoardSlotCommand(2, file2),
            new BoardSlotCommand(3, file3)
        ).stream().filter(m -> m.file() != null && !m.file().isEmpty()).toList();

        boardService.updateBoard(boardId, request, files, userPrincipal);

        return ResponseEntity.ok(ApiResponse.success());
    }

}
