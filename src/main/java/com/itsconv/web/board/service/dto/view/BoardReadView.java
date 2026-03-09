package com.itsconv.web.board.service.dto.view;

import java.time.LocalDateTime;

import com.itsconv.web.board.domain.Board;
import com.itsconv.web.board.domain.BoardType;

import lombok.Builder;

@Builder
public record BoardReadView(
    Long id,
    String createName,
    LocalDateTime createDate,
    String title,
    String contents,
    BoardType type
) {
    public static BoardReadView from(Board b) {
        return BoardReadView.builder()
            .id(b.getId())
            .createName(b.getCreateName())
            .createDate(b.getCreateDate())
            .title(b.getTitle())
            .contents(b.getContents())
            .type(b.getType())
            .build();
    }
}
