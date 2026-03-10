package com.itsconv.web.image.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageFileStorage {

    StoredImageFile store(MultipartFile uploadFile);

    record StoredImageFile(
            String path,
            String uuid,
            String savedFileName,
            Long size
    ) {
    }
}
