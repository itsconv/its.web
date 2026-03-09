package com.itsconv.web.file.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itsconv.web.common.response.ApiResponse;
import com.itsconv.web.file.controller.dto.response.FileEditorResponse;
import com.itsconv.web.file.domain.FileStatus;
import com.itsconv.web.file.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {
    private final FileService fileService;

    @PostMapping(value = "/temp", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FileEditorResponse>> uploadTempFile(
        @RequestPart("file") MultipartFile file
    ) {
        return ResponseEntity.ok(
            ApiResponse.success(fileService.uploadEditorFile(file, FileStatus.TEMP))
        );
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FileEditorResponse>> uploadFile(
        @RequestPart("file") MultipartFile file
    ) {
        return ResponseEntity.ok(
            ApiResponse.success(fileService.uploadEditorFile(file, FileStatus.USED))
        );
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> view(@PathVariable Long id) throws MalformedURLException {
        com.itsconv.web.file.domain.File f = fileService.findFileById(id);

        Path path = Paths.get(f.getPath(), f.getUuid());
        Resource resource = new UrlResource(path.toUri());

        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        try {
            contentType = Files.probeContentType(path);
        } catch (IOException ignored) {}

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CACHE_CONTROL, "no-cache")
            .body(resource);
    }
}
