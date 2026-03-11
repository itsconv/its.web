package com.itsconv.web.file.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itsconv.web.file.domain.File;
import com.itsconv.web.file.domain.FileBoard;
import com.itsconv.web.file.service.dto.command.FileRemoveCommand;
import com.itsconv.web.file.service.dto.view.BoardThumbnailView;

public interface FileBoardRepository extends JpaRepository<FileBoard, Long>, FileBoardQueryRepository{

    @Query("select fd.file.id from FileBoard fd where fd.board.id in (:boardIds)")
    List<Long> findFileIdsByBoardIds(List<Long> boardIds);

    @Query("""
        select new com.itsconv.web.file.service.dto.view.BoardThumbnailView(fd.board.id, fd.file.id)
        from FileBoard fd
        where fd.board.id in :boardIds
          and fd.sortOrder > 0
          and fd.isThumbnail = 'Y'
          and fd.status = 'USED'
    """)
    List<BoardThumbnailView> findThumbnailFileIdsByBoardIds(@Param("boardIds") List<Long> boardIds);

    @Query("select fd.mappedId from FileBoard fd where fd.file.id = :fileId")
    Long findMappedIdByFileId(Long fileId);

    @Query("select fd.file from FileBoard fd where fd.board.id = :#{#command.boardId} and fd.mappedId = :#{#command.mappedId}")
    File findFileByMappedId(@Param("command") FileRemoveCommand command);

    @Query("""
        select fd
        from FileBoard fd
        where fd.mappedId in :mappedIds
    """)
    List<FileBoard> findAllByMappedIds(@Param("mappedIds") List<Long> mappedIds);

    @Query("""
        select fd
        from FileBoard fd
        join fetch fd.file
        where fd.status = :status
          and fd.board is null
          and fd.lastUpdate <= :cutoff
    """)
    List<FileBoard> findExpiredTempFiles(
        @Param("status") String status,
        @Param("cutoff") LocalDateTime cutoff
    );

    @Query("""
        select fd
        from FileBoard fd
        join fetch fd.file
        where fd.mappedId = :mappedId
    """)
    Optional<FileBoard> findWithFileByMappedId(@Param("mappedId") Long mappedId);

    @Query("""
        select fd
        from FileBoard fd
        join fetch fd.file
        where fd.board.id = :boardId
          and fd.sortOrder = 0
          and fd.status = :status
    """)
    List<FileBoard> findUsedEditorFilesByBoardId(
        @Param("boardId") Long boardId,
        @Param("status") String status
    );
}
