package com.itsconv.web.admin.controller.dto;

import com.itsconv.web.user.domain.User;

import java.time.LocalDateTime;

public record AdminUserListResponse(
        Long seq,
        String userId,
        String name,
        LocalDateTime lastLogin,
        LocalDateTime createDate
) {
    public static AdminUserListResponse from(User user) {
        return new AdminUserListResponse(
                user.getSeq(),
                user.getUserId(),
                user.getName(),
                user.getLastLogin(),
                user.getCreateDate()
        );
    }
}
