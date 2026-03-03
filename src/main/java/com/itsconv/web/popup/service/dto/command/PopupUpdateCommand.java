package com.itsconv.web.popup.service.dto.command;

import java.time.LocalDate;

public record PopupUpdateCommand(
        Long seq,
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
}
