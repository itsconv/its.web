package com.itsconv.web.history.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itsconv.web.common.response.ApiResponse;
import com.itsconv.web.history.controller.dto.HistoryTopModifyRequest;
import com.itsconv.web.history.domain.History;
import com.itsconv.web.history.service.HistoryService;
import com.itsconv.web.security.service.UserPrincipal;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping("/{parent}")
    public ResponseEntity<ApiResponse<List<History>>> getByParent(
        @PathVariable String parent) {
        return ResponseEntity.ok(ApiResponse.success(historyService.findByParent(parent)));
    }

    @PostMapping("/{id}/modify")
    public ResponseEntity<ApiResponse<Void>> modifyTopName(@RequestBody HistoryTopModifyRequest request,
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ){
        historyService.updateName(request, userPrincipal);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
