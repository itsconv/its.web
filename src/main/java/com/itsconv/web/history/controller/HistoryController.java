package com.itsconv.web.history.controller;


import com.itsconv.web.common.response.ApiResponse;
import com.itsconv.web.history.controller.dto.request.HistoryPeriodCreateRequest;
import com.itsconv.web.history.controller.dto.request.HistoryPeriodModifyRequest;
import com.itsconv.web.history.domain.HistoryPeriod;
import com.itsconv.web.history.service.HistoryService;
import com.itsconv.web.security.service.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResponse<Void>> modifyTopName(@Valid @RequestBody HistoryPeriodModifyRequest request,
                                                           @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        historyService.updateName(request, userPrincipal);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<ApiResponse<Void>> removeTop(@PathVariable Long id) {
        historyService.deleteTop(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/period")
    public ResponseEntity<ApiResponse<Void>> createTop(@Valid @RequestBody HistoryPeriodCreateRequest request,
                                                       @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        historyService.createTop(request, userPrincipal);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
