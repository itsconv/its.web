package com.itsconv.web.popup.controller;

import com.itsconv.web.popup.controller.dto.PopupCreateRequest;
import com.itsconv.web.popup.controller.dto.PopupUpdateRequest;
import com.itsconv.web.common.response.ApiResponse;
import com.itsconv.web.popup.service.PopupService;
import com.itsconv.web.popup.service.dto.command.PopupDeleteCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/popups")
public class PopupController {

    private final PopupService popupService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createPopup(@RequestBody PopupCreateRequest request) {
        popupService.registerPopup(request.toCommand());
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PutMapping("/{seq}")
    public ResponseEntity<ApiResponse<Void>> modifyPopup(
            @RequestBody PopupUpdateRequest request,
            @PathVariable Long seq
    ) {
        popupService.updatePopup(request.toCommand(seq));
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/{seq}")
    public ResponseEntity<ApiResponse<Void>> removePopup(@PathVariable Long seq) {
        popupService.deletePopup(new PopupDeleteCommand(seq));
        return ResponseEntity.ok(ApiResponse.success());
    }
}
