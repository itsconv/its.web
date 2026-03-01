package com.itsconv.web.history.controller.dto.request;

import com.itsconv.web.history.domain.HistoryYear;
import com.itsconv.web.history.service.dto.command.HistoryItemCreateCommand;

import jakarta.validation.constraints.NotBlank;

public record HistoryItemCreateRequest(
    Long yearId,

    @NotBlank(message = "연혁을 입력해주세요.")
    String content
) {
    public HistoryItemCreateCommand toCommand(HistoryYear year, Integer nextOrder, String requestId) {
        return HistoryItemCreateCommand.from(year, content, nextOrder, requestId);

    }
}
