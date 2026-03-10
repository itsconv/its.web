package com.itsconv.web.file.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itsconv.web.file.domain.FileDetail;

public interface FileDetailRepository extends JpaRepository<FileDetail, Long>, FileDetailQueryRepository{

    @Query("select fd.file.id from FileDetail fd where fd.board.id in (:boardIds)")
    List<Long> findFileIdsByBoardIds(List<Long> boardIds);

}
