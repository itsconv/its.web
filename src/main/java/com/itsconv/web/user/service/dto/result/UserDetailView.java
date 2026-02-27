package com.itsconv.web.user.service.dto.result;

import com.itsconv.web.user.domain.User;

import java.time.LocalDateTime;

public record UserDetailView(
        String userId,
        String name,
        String memo,
        LocalDateTime lastLogin,
        LocalDateTime createDate
) {
    public static UserDetailView from(User user) {
        return new UserDetailView(
                user.getUserId(),
                user.getName(),
                user.getMemo(),
                user.getLastLogin(),
                user.getCreateDate()
        );
    }
}
