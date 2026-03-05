package com.itsconv.web.board.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itsconv.web.board.controller.dto.request.BoardCopyRequest;
import com.itsconv.web.board.controller.dto.request.BoardMoveRequest;
import com.itsconv.web.board.controller.dto.request.BoardOrderRequest;
import com.itsconv.web.board.service.BoardService;
import com.itsconv.web.common.response.ApiResponse;
import com.itsconv.web.security.service.UserPrincipal;

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
        boardService.delete(targetList);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PutMapping("/order")
    public ResponseEntity<ApiResponse<Void>> modifyOrder(
        @RequestBody List<BoardOrderRequest> list, @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        boardService.updateOrder(list, userPrincipal);
        return ResponseEntity.ok(ApiResponse.success());
    }

}
