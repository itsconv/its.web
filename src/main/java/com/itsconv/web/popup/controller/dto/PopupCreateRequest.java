package com.itsconv.web.popup.controller.dto;

import com.itsconv.web.popup.service.dto.command.PopupRegisterCommand;

import java.time.LocalDate;

public record PopupCreateRequest(
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
    public PopupRegisterCommand toCommand() {
        return new PopupRegisterCommand(
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
