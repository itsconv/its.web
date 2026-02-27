package com.itsconv.web.popup.repository;

import com.itsconv.web.popup.domain.Popup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopupRepository extends JpaRepository<Popup, Long> {
}
