package com.itsconv.web.image.service.dto.view;

import java.util.Map;

public record PageImageUrlsView(
        Map<String, String> imageUrls
) {
    public static PageImageUrlsView of(Map<String, String> imageUrls) {
        return new PageImageUrlsView(Map.copyOf(imageUrls));
    }
}
