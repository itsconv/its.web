package com.itsconv.web.image.controller.dto;

public record ImageFileUpdateRequest(
        String altText,
        String linkUrl
) {
}
