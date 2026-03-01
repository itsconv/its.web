package com.itsconv.web.history.controller.dto.response;

import lombok.Builder;

@Builder
public record HistoryPeriodResponse(
    String startPeriod,
    String endPeriod,
    Long id
) {
    
}
