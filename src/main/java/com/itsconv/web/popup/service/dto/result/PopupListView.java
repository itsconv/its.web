package com.itsconv.web.popup.service.dto.result;

import java.util.List;

public record PopupListView(
        List<PopupDetailView> popups
) {
}
