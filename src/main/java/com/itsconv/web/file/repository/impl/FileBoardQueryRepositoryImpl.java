package com.itsconv.web.file.repository.impl;

import java.util.List;

import com.itsconv.web.file.domain.QFile;
import com.itsconv.web.file.domain.QFileBoard;
import com.itsconv.web.file.repository.FileBoardQueryRepository;
import com.itsconv.web.view.admin.dto.FileAttachView;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileBoardQueryRepositoryImpl implements FileBoardQueryRepository {
    
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FileAttachView> findAttachsByBoardId(Long boardId) {
        QFile f = QFile.file;
        QFileBoard fd = QFileBoard.fileBoard;

        return jpaQueryFactory
            .select(Projections.constructor(
                FileAttachView.class,
                fd.mappedId,
                f.id,
                f.originName,
                fd.isThumbnail,
                fd.sortOrder
            ))
            .from(fd)
            .join(fd.file, f)
            .where(fd.board.id.eq(boardId))
            .orderBy(fd.sortOrder.asc())
            .fetch();
    }
    
}
