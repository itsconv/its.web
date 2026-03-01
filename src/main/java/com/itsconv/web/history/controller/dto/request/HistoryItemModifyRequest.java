package com.itsconv.web.history.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record HistoryItemModifyRequest(
    Long itemId,

    @NotBlank(message = "연혁 내용을 입력해주세요.")
    String content
) {
    
}
