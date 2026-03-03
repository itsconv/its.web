package com.itsconv.web.user.controller.dto;

import com.itsconv.web.user.service.dto.command.UserUpdateCommand;

public record UserUpdateRequest(
        String password,
        String name,
        String memo
) {
    public UserUpdateCommand toCommand(String userId) {
        return UserUpdateCommand.from(userId, password, name, memo);
    }
}
