package com.itsconv.web.image.repository.dto;

import com.itsconv.web.image.service.dto.view.ImageSlotCardView;

public record ImageSlotCardRow(
        Long menuId,
        Long slotId,
        String slotCode,
        String slotName,
        String guideText,
        Integer guideWidth,
        Integer guideHeight,
        Long fileId,
        String filePath,
        String fileOriginName
) {
    public ImageSlotCardView toView() {
        return new ImageSlotCardView(
                slotId,
                slotCode,
                slotName,
                guideText,
                guideWidth,
                guideHeight,
                fileId,
                filePath,
                fileOriginName
        );
    }
}
