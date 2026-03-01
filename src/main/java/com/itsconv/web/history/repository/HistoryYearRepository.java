package com.itsconv.web.history.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itsconv.web.history.domain.HistoryYear;

public interface HistoryYearRepository extends JpaRepository<HistoryYear, Long>{
    
    @Query("select coalesce(max(y.sortOrder), 0) from HistoryYear y")   
    Integer findMaxSortOrder();
}
