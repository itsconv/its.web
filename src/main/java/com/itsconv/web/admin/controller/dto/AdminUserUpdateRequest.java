package com.itsconv.web.admin.controller.dto;

import com.itsconv.web.user.service.dto.command.UserUpdateCommand;

public record AdminUserUpdateRequest(
        String password,
        String name,
        String memo
) {
    public UserUpdateCommand toCommand(String userId) {
        return UserUpdateCommand.from(userId, password, name, memo);
    }
}
