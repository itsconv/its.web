package com.itsconv.web.history.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itsconv.web.common.exception.BusinessException;
import com.itsconv.web.common.exception.ErrorCode;
import com.itsconv.web.history.controller.dto.HistoryTopModifyRequest;
import com.itsconv.web.history.domain.History;
import com.itsconv.web.history.repository.HistoryRepository;
import com.itsconv.web.security.service.UserPrincipal;

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
    public void updateName(HistoryTopModifyRequest request, UserPrincipal userPrincipal) {
        if (request.id() == null) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        History history = historyRepository.findById(request.id()).orElseThrow();

        history.updatePeriodName(request.start(), request.end(), userPrincipal.getName());
    }
}
