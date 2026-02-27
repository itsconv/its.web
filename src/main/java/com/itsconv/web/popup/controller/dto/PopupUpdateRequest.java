package com.itsconv.web.popup.controller.dto;

import com.itsconv.web.popup.service.dto.command.PopupUpdateCommand;

import java.time.LocalDate;

public record PopupUpdateRequest(
        String title,
        String contents,
        LocalDate startDate,
        LocalDate endDate,
        String useYn,
        Integer positionX,
        Integer positionY,
        Integer sizeW,
        Integer sizeH,
        String redirectUrl
) {
    public PopupUpdateCommand toCommand(Long seq) {
        return new PopupUpdateCommand(
                seq,
                title,
                contents,
                startDate,
                endDate,
                useYn,
                positionX,
                positionY,
                sizeW,
                sizeH,
                redirectUrl
        );
    }
}
