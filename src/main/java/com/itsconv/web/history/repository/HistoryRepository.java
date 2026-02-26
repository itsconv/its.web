package com.itsconv.web.history.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itsconv.web.history.domain.History;

public interface HistoryRepository extends JpaRepository<History, Integer>{
    List<History> findByParent(String historyParent);    
}