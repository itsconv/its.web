package com.itsconv.web.security.controller.dto;

import java.util.List;

public record AuthStatusResponse(
        boolean loggedIn,
        Long userSeq,
        String userId,
        String name,
        List<String> authorities
) {
}
