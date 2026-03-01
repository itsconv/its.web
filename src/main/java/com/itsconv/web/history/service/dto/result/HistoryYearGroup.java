package com.itsconv.web.history.service.dto.result;

import java.util.List;

import lombok.Builder;

@Builder
public record HistoryYearGroup(
    Long yearId,
    String year,
    Integer sortOrder,
    List<HistoryItemRow> items
) {
    
}
