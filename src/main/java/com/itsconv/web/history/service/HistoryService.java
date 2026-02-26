package com.itsconv.web.history.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itsconv.web.history.controller.dto.HistoryTopModifyRequest;
import com.itsconv.web.history.domain.History;
import com.itsconv.web.history.repository.HistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;

    @Transactional(readOnly = true)
    public List<History> findByParent(String parent) {
        return historyRepository.findByParent(parent);
    }

    @Transactional
    public void updateName(Integer id, HistoryTopModifyRequest request) {
        History history = historyRepository.findById(id).orElseThrow();

        
    }
}
