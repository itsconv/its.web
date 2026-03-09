package com.itsconv.web.file.repository;

import java.util.List;

import com.itsconv.web.view.admin.dto.FileAttachView;

public interface FileDetailQueryRepository {
    List<FileAttachView> findAttachsByBoardId(Long boardId);
}
