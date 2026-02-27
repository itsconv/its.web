package com.itsconv.web.history.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itsconv.web.common.exception.BusinessException;
import com.itsconv.web.common.exception.ErrorCode;
import com.itsconv.web.history.controller.dto.HistoryTopCreateRequest;
import com.itsconv.web.history.controller.dto.HistoryTopModifyRequest;
import com.itsconv.web.history.domain.HistoryPeriod;
import com.itsconv.web.history.repository.HistoryPeriodRepository;
import com.itsconv.web.security.service.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryPeriodRepository historyPeriodRepository;

    @Transactional(readOnly = true)
    public List<HistoryPeriod> findPeriod(String parent) {
        return historyPeriodRepository.findAll(Sort.by("sortOrder").ascending());
    }

    @Transactional
    public void updateName(HistoryTopModifyRequest request, UserPrincipal userPrincipal) {
        if (request.id() == null) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        HistoryPeriod historyPeriod = historyPeriodRepository.findById(request.id()).orElseThrow();

        historyPeriod.updatePeriodName(request.start(), request.end(), userPrincipal.getUsername());
    }

    @Transactional
    public void deleteTop(Long id) {
        historyPeriodRepository.deleteById(id);
    }

    @Transactional
    public void createTop(HistoryTopCreateRequest request, UserPrincipal userPrincipal) {
        HistoryPeriod historyPeriod = new HistoryPeriod();

        Integer nextOrder = historyPeriodRepository.findMaxSortOrder() + 1;

        historyPeriod.saveTopHistory(request.start(), request.end(), userPrincipal.getUsername(), nextOrder);
        historyPeriodRepository.save(historyPeriod);
    }
}