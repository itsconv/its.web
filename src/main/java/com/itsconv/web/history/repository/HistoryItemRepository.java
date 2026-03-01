package com.itsconv.web.history.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itsconv.web.history.domain.HistoryItem;

public interface HistoryItemRepository extends JpaRepository<HistoryItem, Long>{
    @Query("select coalesce(max(i.sortOrder), 0) from HistoryItem i")
    Integer findMaxSortOrder();
}
