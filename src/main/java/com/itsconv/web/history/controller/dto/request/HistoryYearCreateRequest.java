package com.itsconv.web.history.controller.dto.request;

import com.itsconv.web.history.domain.HistoryPeriod;
import com.itsconv.web.history.service.dto.command.HistoryYearCreateCommand;

import jakarta.validation.constraints.NotBlank;

public record HistoryYearCreateRequest(
    @NotBlank(message = "연도를 입력해주세요.")
    String year,
    
    Long periodId
) {
    public HistoryYearCreateCommand toCommand(Integer nextOrder, String requestId, HistoryPeriod period) {
        return HistoryYearCreateCommand.from(period, year, nextOrder, requestId);
    }
}
