package com.itsconv.web.user.service.dto.result;

import com.itsconv.web.user.domain.User;

import java.util.List;

public record UserListView(
        List<User> users
) {
}
