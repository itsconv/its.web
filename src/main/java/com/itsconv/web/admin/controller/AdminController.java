package com.itsconv.web.admin.controller;

import com.itsconv.web.admin.controller.dto.AdminUserUpdateRequest;
import com.itsconv.web.common.response.ApiResponse;
import com.itsconv.web.user.service.UserService;
import com.itsconv.web.user.service.dto.command.UserDeleteCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PutMapping("/api/admin/users/{userId}")
    public ResponseEntity<ApiResponse<Void>> updateAdmin(
            @RequestBody AdminUserUpdateRequest request,
            @PathVariable String userId
    ) {
        userService.updateUser(request.toCommand(userId));
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/api/admin/users/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteAdmin(@PathVariable String userId) {
        userService.deleteUser(new UserDeleteCommand(userId));
        return ResponseEntity.ok(ApiResponse.success());
    }
}
