package com.itsconv.web.view.admin.dto;

import java.time.LocalDateTime;

import com.itsconv.web.board.domain.Board;
import com.itsconv.web.board.domain.BoardType;

import lombok.Builder;

@Builder
public record BoardDetailView(
    Long id,
    String createName,
    LocalDateTime createDate,
    String title,
    String contents,
    Integer viewCount,
    BoardType type,
    BoardPost prevPost,
    BoardPost nextPost
) {
    public static BoardDetailView from(Board b, BoardPost prev, BoardPost next) {
        return BoardDetailView.builder()
            .id(b.getId())
            .createName(b.getCreateName())
            .createDate(b.getCreateDate())
            .title(b.getTitle())
            .contents(b.getContents())
            .viewCount(b.getViewCount())
            .type(b.getType())
            .prevPost(prev)
            .nextPost(next)
            .build();
    }
}
