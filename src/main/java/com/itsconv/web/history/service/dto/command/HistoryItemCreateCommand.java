package com.itsconv.web.history.service.dto.command;

import com.itsconv.web.history.domain.HistoryYear;

import lombok.Builder;

@Builder
public record HistoryItemCreateCommand(
    HistoryYear year,
    String content,
    Integer nextOrder,
    String requestId
) {
    public static HistoryItemCreateCommand from(
        HistoryYear year, String content, Integer nextOrder, String requestId
    ) {
        return HistoryItemCreateCommand.builder()
            .year(year)
            .content(content)
            .nextOrder(nextOrder)
            .requestId(requestId)
            .build();
    }
}
