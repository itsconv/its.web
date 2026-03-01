package com.itsconv.web.history.service.dto.command;

import com.itsconv.web.history.domain.HistoryPeriod;

import lombok.Builder;

@Builder
public record HistoryYearCreateCommand(
    HistoryPeriod period,
    String year,
    Integer nextOrder,
    String requestId
) {
    public static HistoryYearCreateCommand from(
        HistoryPeriod period, String year, Integer nextOrder, String requestId
    ) {
        return HistoryYearCreateCommand.builder()
            .period(period)
            .year(year)
            .nextOrder(nextOrder)
            .requestId(requestId)
            .build();
    }
}
