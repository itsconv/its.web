package com.itsconv.web.history.service.dto.result;

public record HistoryItemListView(
    Long yearId,
    String year,
    Integer yearOrder,
    Long itemId,
    String content,
    Integer itemOrder
) {
    
}
