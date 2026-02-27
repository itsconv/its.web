package com.itsconv.web.view.admin.dto;

import com.itsconv.web.popup.service.dto.result.PopupDetailView;

import java.time.LocalDate;

public record PopupFormView(
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
    public static PopupFormView createDefault() {
        return new PopupFormView(
                "",
                "",
                LocalDate.now(),
                LocalDate.now(),
                "Y",
                0,
                0,
                600,
                700,
                ""
        );
    }

    public static PopupFormView from(PopupDetailView popup) {
        return new PopupFormView(
                popup.title(),
                popup.contents(),
                popup.startDate(),
                popup.endDate(),
                popup.useYn(),
                popup.positionX(),
                popup.positionY(),
                popup.sizeW(),
                popup.sizeH(),
                popup.redirectUrl()
        );
    }
}
