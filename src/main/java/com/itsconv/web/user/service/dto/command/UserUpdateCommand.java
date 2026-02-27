package com.itsconv.web.user.service.dto.command;

public record UserUpdateCommand(
        String userId,
        String password,
        String name,
        String memo
) {
    public static UserUpdateCommand from(String userId, String password, String name, String memo) {
        return new UserUpdateCommand(userId, password, name, memo);
    }
}
