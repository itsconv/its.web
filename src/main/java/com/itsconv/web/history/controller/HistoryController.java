package com.itsconv.web.history.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itsconv.web.common.response.ApiResponse;
import com.itsconv.web.history.controller.dto.HistoryTopCreateRequest;
import com.itsconv.web.history.controller.dto.HistoryTopModifyRequest;
import com.itsconv.web.history.domain.HistoryPeriod;
import com.itsconv.web.history.service.HistoryService;
import com.itsconv.web.security.service.UserPrincipal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping("/period")
    public ResponseEntity<ApiResponse<List<HistoryPeriod>>> getPeriod() {
        return ResponseEntity.ok(ApiResponse.success(historyService.findPeriod(null)));
    }

    @PutMapping("/{id}/modify")
    public ResponseEntity<ApiResponse<Void>> modifyTopName(@Valid @RequestBody HistoryTopModifyRequest request,
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ){
        historyService.updateName(request, userPrincipal);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<ApiResponse<Void>> removeTop(@PathVariable Long id) {
        historyService.deleteTop(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/period")
    public ResponseEntity<ApiResponse<Void>> createTop(@Valid @RequestBody HistoryTopCreateRequest request,
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        historyService.createTop(request, userPrincipal);
        return ResponseEntity.ok(ApiResponse.success());
    }
}