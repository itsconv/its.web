package com.itsconv.web.history.service.dto.result;

import lombok.Builder;

@Builder
public record HistoryItemRow(
    Long itemId,
    String content,
    Integer sortOrder
) {
    
}
