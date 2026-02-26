package com.itsconv.web.security.controller;

import java.util.List;

import com.itsconv.web.common.response.ApiResponse;
import com.itsconv.web.security.controller.dto.AuthStatusResponse;
import com.itsconv.web.security.service.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<AuthStatusResponse>> getAuthStatus(@AuthenticationPrincipal UserPrincipal principal) {
        if (principal == null) {
            return ResponseEntity.ok(ApiResponse.success(
                    new AuthStatusResponse(false, null, null, null, List.of())
            ));
        }

        return ResponseEntity.ok(ApiResponse.success(
                new AuthStatusResponse(
                        true,
                        principal.getUserSeq(),
                        principal.getUsername(),
                        principal.getName(),
                        principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()))
        );
    }
}
