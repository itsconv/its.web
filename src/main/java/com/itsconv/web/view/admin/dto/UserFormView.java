package com.itsconv.web.view.admin.dto;

import com.itsconv.web.user.service.dto.result.UserDetailView;

public record UserFormView(
        String password,
        String name,
        String memo
) {
    public static UserFormView empty() {
        return new UserFormView(null, "", "");
    }

    public static UserFormView from(UserDetailView user) {
        return new UserFormView(null, user.name(), user.memo());
    }
}
