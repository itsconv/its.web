package com.itsconv.web.user.controller;

import com.itsconv.web.user.controller.dto.UserCreateRequest;
import com.itsconv.web.user.controller.dto.UserUpdateRequest;
import com.itsconv.web.common.response.ApiResponse;
import com.itsconv.web.user.service.UserService;
import com.itsconv.web.user.service.dto.command.UserDeleteCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createUser(@RequestBody UserCreateRequest request) {
        userService.registerUser(request.toCommand());
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> modifyUser(
            @RequestBody UserUpdateRequest request,
            @PathVariable String userId
    ) {
        userService.updateUser(request.toCommand(userId));
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> removeUser(@PathVariable String userId) {
        userService.deleteUser(new UserDeleteCommand(userId));
        return ResponseEntity.ok(ApiResponse.success());
    }
}
