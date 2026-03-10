package com.itsconv.web.file.service.dto.command;

import com.itsconv.web.board.domain.Board;
import com.itsconv.web.file.domain.File;

public record FileBoardCommand(
    File file,
    Board board,
    String isThumbnail,
    Integer sortOrder,
    String status
) {
    
}
