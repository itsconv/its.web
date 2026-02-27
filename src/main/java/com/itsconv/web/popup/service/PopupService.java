package com.itsconv.web.popup.service;

import com.itsconv.web.common.exception.BusinessException;
import com.itsconv.web.common.exception.ErrorCode;
import com.itsconv.web.popup.domain.Popup;
import com.itsconv.web.popup.repository.PopupRepository;
import com.itsconv.web.popup.service.dto.command.PopupDeleteCommand;
import com.itsconv.web.popup.service.dto.command.PopupRegisterCommand;
import com.itsconv.web.popup.service.dto.command.PopupUpdateCommand;
import com.itsconv.web.popup.service.dto.result.PopupDetailView;
import com.itsconv.web.popup.service.dto.result.PopupListView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopupService {

    private final PopupRepository popupRepository;

    @Transactional(readOnly = true)
    public PopupListView findPopupList() {
        List<PopupDetailView> popups = popupRepository.findAll().stream()
                .map(PopupDetailView::from)
                .toList();
        return new PopupListView(popups);
    }

    @Transactional(readOnly = true)
    public PopupDetailView findPopup(Long seq) {
        Popup popup = findPopupEntity(seq);
        return PopupDetailView.from(popup);
    }

    @Transactional
    public void registerPopup(PopupRegisterCommand command) {
        Popup popup = Popup.create(
                command.title(),
                command.contents(),
                command.startDate(),
                command.endDate(),
                command.useYn(),
                command.positionX(),
                command.positionY(),
                command.sizeW(),
                command.sizeH(),
                command.redirectUrl()
        );
        popupRepository.save(popup);
    }

    @Transactional
    public void updatePopup(PopupUpdateCommand command) {
        Popup popup = findPopupEntity(command.seq());
        popup.update(
                command.title(),
                command.contents(),
                command.startDate(),
                command.endDate(),
                command.useYn(),
                command.positionX(),
                command.positionY(),
                command.sizeW(),
                command.sizeH(),
                command.redirectUrl()
        );
    }

    @Transactional
    public void deletePopup(PopupDeleteCommand command) {
        Popup popup = findPopupEntity(command.seq());
        popup.delete();
    }

    private Popup findPopupEntity(Long seq) {
        return popupRepository.findById(seq)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_NOT_FOUND));
    }
}
