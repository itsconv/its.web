package com.itsconv.web.popup.service.dto.result;

import com.itsconv.web.popup.domain.Popup;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PopupDetailView(
        Long seq,
        String title,
        String contents,
        LocalDate startDate,
        LocalDate endDate,
        LocalDateTime createDate,
        String useYn,
        Integer positionX,
        Integer positionY,
        Integer sizeW,
        Integer sizeH,
        String redirectUrl
) {
    public static PopupDetailView from(Popup popup) {
        return new PopupDetailView(
                popup.getSeq(),
                popup.getTitle(),
                popup.getContents(),
                popup.getStartDate(),
                popup.getEndDate(),
                popup.getCreateDate(),
                popup.getUseYn(),
                popup.getPositionX(),
                popup.getPositionY(),
                popup.getSizeW(),
                popup.getSizeH(),
                popup.getRedirectUrl()
        );
    }
}
