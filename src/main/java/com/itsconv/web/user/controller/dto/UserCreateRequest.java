package com.itsconv.web.user.controller.dto;

import com.itsconv.web.user.service.dto.command.UserRegisterCommand;

public record UserCreateRequest(
        String userId,
        String password,
        String name,
        String memo
) {
    public UserRegisterCommand toCommand() {
        return UserRegisterCommand.from(userId, password, name, memo);
    }
}
