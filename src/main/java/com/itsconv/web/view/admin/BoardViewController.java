package com.itsconv.web.view.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itsconv.web.board.controller.dto.request.BoardListRequest;
import com.itsconv.web.board.controller.dto.response.BoardAttachedResponse;
import com.itsconv.web.board.controller.dto.response.BoardListResponse;
import com.itsconv.web.board.domain.BoardType;
import com.itsconv.web.board.service.BoardService;
import com.itsconv.web.security.service.UserPrincipal;
import com.itsconv.web.view.admin.dto.BoardDetailView;
import com.itsconv.web.view.admin.dto.BoardFormView;

import lombok.RequiredArgsConstructor;

/**
 * Temporary ViewController
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/bbs")
public class BoardViewController {
    private final BoardService boardService;

    @GetMapping("/{typePath}")
    public String bbsList(@PathVariable String typePath, BoardListRequest request, Model model) {
        BoardType boardType = BoardType.from(typePath);
        request.setType(boardType);

        Page<BoardListResponse> page = boardService.findBoardList(request);

        model.addAttribute("boardPage", page);
        model.addAttribute("boardRequest", request);
        model.addAttribute("boardTypes", List.of(BoardType.values()));
        
        return boardType.listView();
    }

    @GetMapping("/edit/{typePath}")
    public String loadCreateForm(
        @PathVariable String typePath, Model model, @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        BoardType boardType = BoardType.from(typePath);

        model.addAttribute("boardId", null);
        model.addAttribute("isCreate", true);
        model.addAttribute("boardForm", BoardFormView.create(boardType, userPrincipal.getName()));
        
        return "admin/bbs/edit";
    }

    @GetMapping("/edit/{typePath}/{id}")
    public String loadEditForm(
        @PathVariable("typePath") String typePath, @PathVariable("id") Long id,Model model
    ) {
        BoardAttachedResponse attachedBoard = boardService.findBoardOne(id);

        model.addAttribute("boardId", id);
        model.addAttribute("isCreate", false);
        model.addAttribute("boardForm", BoardFormView.edit(attachedBoard));
        
        return "admin/bbs/edit";
    }

    @GetMapping("/detail/{typePath}")
    public String loadDetailForm(@PathVariable String typePath, @RequestParam Long id, Model model) {
        BoardDetailView view = boardService.getBoardDetailView(id);
        BoardAttachedResponse attachedBoard = boardService.findBoardOne(id);

        model.addAttribute("boardId", id);
        model.addAttribute("boardDetail", view);
        model.addAttribute("boardAttaches", attachedBoard.attaches());

        return "admin/bbs/detail";
    }
    
}


