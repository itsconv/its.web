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
import com.itsconv.web.history.controller.dto.request.HistoryItemCreateRequest;
import com.itsconv.web.history.controller.dto.request.HistoryItemModifyRequest;
import com.itsconv.web.history.controller.dto.request.HistoryPeriodCreateRequest;
import com.itsconv.web.history.controller.dto.request.HistoryPeriodModifyRequest;
import com.itsconv.web.history.controller.dto.request.HistoryYearCreateRequest;
import com.itsconv.web.history.controller.dto.response.HistoryPeriodResponse;
import com.itsconv.web.history.service.HistoryService;
import com.itsconv.web.history.service.dto.result.HistoryYearGroup;
import com.itsconv.web.security.service.UserPrincipal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping("/period")
    public ResponseEntity<ApiResponse<List<HistoryPeriodResponse>>> getPeriod() {
        return ResponseEntity.ok(ApiResponse.success(historyService.findPeriod(null)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> modifyTopName(@Valid @RequestBody HistoryPeriodModifyRequest request,
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ){
        historyService.updatePeriod(request, userPrincipal);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> removeTop(@PathVariable Long id) {
        historyService.deletePeriod(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/period")
    public ResponseEntity<ApiResponse<Void>> createTop(@Valid @RequestBody HistoryPeriodCreateRequest request,
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        historyService.registerPeriod(request, userPrincipal);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/year/{periodId}")
    public ResponseEntity<ApiResponse<List<HistoryYearGroup>>> getYearGroup(@PathVariable Long periodId) {
        return ResponseEntity.ok(ApiResponse.success(historyService.findYearGroupByPeriodId(periodId)));
    }

    @PostMapping("/year")
    public ResponseEntity<ApiResponse<Void>> createYear(@Valid @RequestBody HistoryYearCreateRequest request,
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        historyService.registerYear(request, userPrincipal);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/year/{yearId}")
    public ResponseEntity<ApiResponse<Void>> removeYear(@PathVariable Long yearId) {
        historyService.deleteYear(yearId);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PutMapping("/item/{itemId}")
    public ResponseEntity<ApiResponse<Void>> modifyItem(@Valid @RequestBody HistoryItemModifyRequest request,
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        historyService.updateItem(request, userPrincipal);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/item")
    public ResponseEntity<ApiResponse<Void>> createItem(@Valid @RequestBody HistoryItemCreateRequest request,
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        historyService.registerItem(request, userPrincipal);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<ApiResponse<Void>> removeItem(@PathVariable Long itemId) {
        historyService.deleteItem(itemId);
        return ResponseEntity.ok(ApiResponse.success());
    }

}