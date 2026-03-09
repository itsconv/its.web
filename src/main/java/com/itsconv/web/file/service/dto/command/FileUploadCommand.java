package com.itsconv.web.file.service.dto.command;

public record FileUploadCommand(
    Long size,
    String originName,
    String path,
    String uuid
) {
    
}
