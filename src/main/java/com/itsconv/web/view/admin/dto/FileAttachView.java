package com.itsconv.web.view.admin.dto;

public record FileAttachView(
    Long detailId,
    Long fileId,
    String originName,
    boolean isThumbnail,
    Integer sortOrder
) {
    
}
