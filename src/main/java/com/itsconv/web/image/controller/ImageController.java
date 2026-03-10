package com.itsconv.web.image.controller;

import com.itsconv.web.common.response.ApiResponse;
import com.itsconv.web.image.controller.dto.ImageFileUpdateRequest;
import com.itsconv.web.image.service.ImageService;
import com.itsconv.web.security.service.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping(value = "/{slotId}/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Void>> modifyImageFile(
            @PathVariable Long slotId,
            @RequestPart("request") ImageFileUpdateRequest request,
            @RequestPart("file") MultipartFile file,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        imageService.updateImageFile(slotId, file, request.altText(), request.linkUrl(), principal.getUsername());
        return ResponseEntity.ok(ApiResponse.success());
    }
}
