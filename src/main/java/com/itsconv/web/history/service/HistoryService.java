package com.itsconv.web.history.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itsconv.web.history.domain.History;
import com.itsconv.web.history.repository.HistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;

    public List<History> findByParent(String parent) {
        return historyRepository.findByHistoryParent(parent);
    }
}
