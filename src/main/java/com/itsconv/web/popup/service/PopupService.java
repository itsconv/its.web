package com.itsconv.web.popup.service;

import com.itsconv.web.common.exception.BusinessException;
import com.itsconv.web.common.exception.ErrorCode;
import com.itsconv.web.popup.domain.Popup;
import com.itsconv.web.popup.repository.PopupRepository;
import com.itsconv.web.popup.service.dto.command.PopupDeleteCommand;
import com.itsconv.web.popup.service.dto.command.PopupRegisterCommand;
import com.itsconv.web.popup.service.dto.command.PopupUpdateCommand;
import com.itsconv.web.popup.service.dto.result.PopupDetailView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class PopupService {

    private final PopupRepository popupRepository;

    @Transactional(readOnly = true)
    public Page<PopupDetailView> findPopupList(int page, int size) {
        int safePage = Math.max(1, page) - 1;
        int safeSize = Math.max(1, Math.min(size, 100));
        Pageable pageable = PageRequest.of(safePage, safeSize, Sort.by(Sort.Direction.DESC, "createDate"));
        return popupRepository.findAll(pageable).map(PopupDetailView::from);
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
