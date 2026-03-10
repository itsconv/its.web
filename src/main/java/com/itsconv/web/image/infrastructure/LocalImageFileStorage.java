package com.itsconv.web.image.infrastructure;

import com.itsconv.web.common.exception.BusinessException;
import com.itsconv.web.common.exception.ErrorCode;
import com.itsconv.web.image.service.ImageFileStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
public class LocalImageFileStorage implements ImageFileStorage {

    @Value("${app.image.upload-dir}")
    private String uploadDir;

    @Value("${app.image.url-path}")
    private String urlPath;

    @Override
    public StoredImageFile store(MultipartFile uploadFile) {
        String originalFilename = uploadFile.getOriginalFilename();
        String extension = extractExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        String savedFileName = uuid + extension;
        Path uploadDirectory = Path.of(uploadDir);
        Path targetPath = uploadDirectory.resolve(savedFileName);

        try {
            Files.createDirectories(uploadDirectory);
            uploadFile.transferTo(targetPath);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FILE_UPLOAD_FAILED, e);
        }

        return new StoredImageFile(
                urlPath,
                uuid,
                savedFileName,
                uploadFile.getSize()
        );
    }

    private String extractExtension(String originalFilename) {
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new BusinessException(ErrorCode.FILE_INVALID_EXTENSION);
        }

        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }
}
