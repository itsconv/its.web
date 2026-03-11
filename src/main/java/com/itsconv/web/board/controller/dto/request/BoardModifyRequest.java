package com.itsconv.web.board.controller.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record BoardModifyRequest(
    @NotBlank(message = "제목을 입력해주세요.")
    String title,

    @NotBlank(message = "내용을 입력해주세요.")
    String contents,

    Integer thumbnailOrder,
    
    List<Long> detailIds,

    List<Long> removeMappedIds,

    List<Long> removeEditorDetailIds
) {
    
}
