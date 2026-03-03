package com.itsconv.web.user.service.dto.result;

import java.util.List;

public record UserListView(
        List<UserDetailView> users
) {
}
