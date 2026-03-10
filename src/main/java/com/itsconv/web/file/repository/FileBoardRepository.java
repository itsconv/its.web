package com.itsconv.web.file.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itsconv.web.file.domain.FileBoard;

public interface FileBoardRepository extends JpaRepository<FileBoard, Long>, FileBoardQueryRepository{

    @Query("select fd.file.id from FileBoard fd where fd.board.id in (:boardIds)")
    List<Long> findFileIdsByBoardIds(List<Long> boardIds);

}
