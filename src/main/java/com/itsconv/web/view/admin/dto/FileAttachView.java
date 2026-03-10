package com.itsconv.web.view.admin.dto;

public record FileAttachView(
    Long detailId,
    Long fileId,
    String originName,
    String isThumbnail,
    Integer sortOrder
) {
    
}
