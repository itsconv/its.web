package com.itsconv.web.view.admin;

import com.itsconv.web.board.controller.dto.request.BoardListRequest;
import com.itsconv.web.board.controller.dto.response.BoardListResponse;
import com.itsconv.web.board.domain.BoardType;
import com.itsconv.web.board.service.BoardService;
import com.itsconv.web.common.exception.BusinessException;
import com.itsconv.web.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 임시 ViewController
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/bbs")
public class BoardViewController {
    private final BoardService boardService;

    @GetMapping("/{typePath}")
    public String bbsList(@PathVariable String typePath, BoardListRequest request, Model model) {
        if (typePath == null) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }
        BoardType boardType = BoardType.from(typePath);
        request.setType(boardType);

        Page<BoardListResponse> page = boardService.findBoardList(request);

        model.addAttribute("boardPage", page);
        model.addAttribute("boardRequest", request);
        model.addAttribute("boardTypes", List.of(BoardType.values()));
        
        return boardType.listView();
    }
}
