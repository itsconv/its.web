package com.itsconv.web.board.controller.dto.request;

import java.util.List;

import com.itsconv.web.board.domain.BoardType;

import jakarta.validation.constraints.NotBlank;

public record BoardCreateRequest(
    BoardType type,

    @NotBlank(message = "제목을 입력해주세요.")
    String title,

    @NotBlank(message = "내용을 입력해주세요.")
    String contents,

    Integer thumbnailOrder,
    
    List<Long> detailIds
) {
    
}
