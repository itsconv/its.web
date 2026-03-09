package com.itsconv.web.board.controller.dto.response;

import java.util.List;

import com.itsconv.web.board.service.dto.view.BoardReadView;
import com.itsconv.web.view.admin.dto.FileAttachView;

public record BoardAttachedResponse(
    BoardReadView board,
    List<FileAttachView> attaches
) {
    
}
