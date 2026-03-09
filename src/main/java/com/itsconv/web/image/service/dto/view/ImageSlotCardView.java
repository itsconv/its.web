package com.itsconv.web.image.service.dto.view;

public record ImageSlotCardView(
        Long slotId,
        String slotCode,
        String slotName,
        String guideText,
        String guideSize,
        Long fileId,
        String filePath,
        String fileOriginName
) {
    public String thumbnailUrl() {
        if (filePath == null || filePath.isBlank() || fileOriginName == null || fileOriginName.isBlank()) {
            return null;
        }

        return filePath + "/" + fileOriginName;
    }

    public String landingUrl() {
        if (slotCode == null || slotCode.isBlank()) {
            return "/front/trading-center";
        }

        return "/front/trading-center#" + slotCode;
    }
}
