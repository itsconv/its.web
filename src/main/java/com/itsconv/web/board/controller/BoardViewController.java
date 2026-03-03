package com.itsconv.web.board.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itsconv.web.board.controller.dto.request.BoardListRequest;
import com.itsconv.web.board.controller.dto.response.BoardListResponse;
import com.itsconv.web.board.domain.BoardType;
import com.itsconv.web.board.service.BoardService;
import com.itsconv.web.common.exception.BusinessException;
import com.itsconv.web.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

/**
 * 임시 ViewController
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/bbs")
public class BoardViewController {
    private final BoardService boardService;

    @GetMapping("/{typePath}")
    public String bbsList(@PathVariable String typePath ,BoardListRequest request, Model model) {
        if (typePath == null) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }
        BoardType boardType = BoardType.from(typePath);
        request.setType(boardType);

        Page<BoardListResponse> page = boardService.findBoardList(request);
        
        model.addAttribute("page", page);
        model.addAttribute("req", request);

        return boardType.listView();
    }
}
