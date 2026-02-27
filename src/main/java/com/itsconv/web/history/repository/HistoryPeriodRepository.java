package com.itsconv.web.history.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itsconv.web.history.domain.HistoryPeriod;

public interface HistoryPeriodRepository extends JpaRepository<HistoryPeriod, Long>{
    @Query("select coalesce(max(p.sortOrder), 0) from HistoryPeriod p")
    Integer findMaxSortOrder();

}