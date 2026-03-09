package com.itsconv.web.board.service.dto.command;

import org.springframework.web.multipart.MultipartFile;

public record BoardSlotCommand(
    Integer slotNo,
    MultipartFile file
) {
    
}
