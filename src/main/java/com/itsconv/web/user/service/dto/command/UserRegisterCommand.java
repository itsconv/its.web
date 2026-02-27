package com.itsconv.web.user.service.dto.command;

public record UserRegisterCommand(
        String userId,
        String password,
        String name,
        String memo
) {
    public static UserRegisterCommand from(
            String userId,
            String password,
            String name,
            String memo
    ) {
        return new UserRegisterCommand(userId, password, name, memo);
    }
}
