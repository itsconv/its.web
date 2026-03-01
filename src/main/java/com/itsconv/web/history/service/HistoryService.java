package com.itsconv.web.history.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itsconv.web.common.exception.BusinessException;
import com.itsconv.web.common.exception.ErrorCode;
import com.itsconv.web.history.controller.dto.request.HistoryItemCreateRequest;
import com.itsconv.web.history.controller.dto.request.HistoryItemModifyRequest;
import com.itsconv.web.history.controller.dto.request.HistoryPeriodCreateRequest;
import com.itsconv.web.history.controller.dto.request.HistoryPeriodModifyRequest;
import com.itsconv.web.history.controller.dto.request.HistoryYearCreateRequest;
import com.itsconv.web.history.controller.dto.response.HistoryPeriodResponse;
import com.itsconv.web.history.domain.HistoryItem;
import com.itsconv.web.history.domain.HistoryPeriod;
import com.itsconv.web.history.domain.HistoryYear;
import com.itsconv.web.history.repository.HistoryItemQueryRepository;
import com.itsconv.web.history.repository.HistoryItemRepository;
import com.itsconv.web.history.repository.HistoryPeriodRepository;
import com.itsconv.web.history.repository.HistoryYearRepository;
import com.itsconv.web.history.service.dto.command.HistoryYearCreateCommand;
import com.itsconv.web.history.service.dto.result.HistoryItemListView;
import com.itsconv.web.history.service.dto.result.HistoryItemRow;
import com.itsconv.web.history.service.dto.result.HistoryYearGroup;
import com.itsconv.web.security.service.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryPeriodRepository historyPeriodRepository;
    private final HistoryYearRepository historyYearRepository;
    private final HistoryItemRepository historyItemRepository;
    private final HistoryItemQueryRepository historyItemQueryRepository;

    @Transactional(readOnly = true)
    public List<HistoryPeriodResponse> findPeriod(String parent) {
        List<HistoryPeriod> periodList = historyPeriodRepository.findAll(Sort.by("sortOrder").ascending());
        return periodList.stream()
                .map(m -> HistoryPeriodResponse.builder()
                    .startPeriod(m.getStartPeriod())
                    .endPeriod(m.getEndPeriod())
                    .id(m.getId())
                    .build()
                ).toList();
    }

    @Transactional
    public void updatePeriod(HistoryPeriodModifyRequest request, UserPrincipal userPrincipal) {
        if (request.id() == null) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        HistoryPeriod historyPeriod = historyPeriodRepository.findById(request.id()).orElseThrow();

        historyPeriod.updatePeriodName(request.start(), request.end(), userPrincipal.getUsername());
    }

    @Transactional
    public void deletePeriod(Long id) {
        historyPeriodRepository.deleteById(id);
    }

    @Transactional
    public void registerPeriod(HistoryPeriodCreateRequest request, UserPrincipal userPrincipal) {
        HistoryPeriod historyPeriod = new HistoryPeriod();

        Integer nextOrder = historyPeriodRepository.findMaxSortOrder() + 1;

        historyPeriod.saveTopHistory(request.start(), request.end(), userPrincipal.getUsername(), nextOrder);
        historyPeriodRepository.save(historyPeriod);
    }

    @Transactional(readOnly = true)
    public List<HistoryYearGroup> findYearGroupByPeriodId(Long periodId) {
        if (periodId == null) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        List<HistoryItemListView> rows = historyItemQueryRepository.findYearItemByPeriodId(periodId);

        Map<Long, HistoryYearGroup> yearMap = new LinkedHashMap<>();

        for (HistoryItemListView row : rows) {
            // yearId = key
            HistoryYearGroup group = yearMap.get(row.yearId());

            //Left JOIN 결과 만들기
            if (group == null) {
                group = HistoryYearGroup.builder()
                        .yearId(row.yearId())
                        .year(row.year())
                        .sortOrder(row.yearOrder())
                        .items(new ArrayList<>())
                        .build();
                yearMap.put(row.yearId(), group);
            }

            if (row.itemId() != null) {
                group.items().add(
                    HistoryItemRow.builder()
                        .itemId(row.itemId())
                        .content(row.content())
                        .sortOrder(row.itemOrder())
                        .build()
                );
            }
        }

        return new ArrayList<>(yearMap.values());
    }

    @Transactional
    public void registerYear(HistoryYearCreateRequest request, UserPrincipal userPrincipal) {
        if (request.periodId() == null) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        HistoryPeriod period = historyPeriodRepository.findById(request.periodId())
            .orElseThrow();

        Integer nextOrder = historyYearRepository.findMaxSortOrder() + 1;

        HistoryYearCreateCommand command = request.toCommand(nextOrder, userPrincipal.getUsername(), period);

        HistoryYear historyYear = new HistoryYear();

        historyYear.saveYear(command);
        historyYearRepository.save(historyYear);
    }

    @Transactional
    public void deleteYear(Long yearId) {
        if (yearId == null) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        historyYearRepository.deleteById(yearId);
    }

    @Transactional
    public void updateItem(HistoryItemModifyRequest request, UserPrincipal userPrincipal) {
        if (request.itemId() == null) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }
        HistoryItem historyItem = historyItemRepository.findById(request.itemId())
            .orElseThrow();

        historyItem.updateContent(request.content(), userPrincipal.getUsername());
    }

    @Transactional
    public void registerItem(HistoryItemCreateRequest request, UserPrincipal userPrincipal) {
        if (request.yearId() == null) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        HistoryYear historyYear = historyYearRepository.findById(request.yearId())
            .orElseThrow();

        Integer nextOrder = historyItemRepository.findMaxSortOrder() + 1;

        HistoryItem historyItem = new HistoryItem();

        historyItem.saveContent(request.toCommand(historyYear, nextOrder, userPrincipal.getUsername()));

        historyItemRepository.save(historyItem);
    }

    @Transactional
    public void deleteItem(Long itemId) {
        if (itemId == null) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }
        historyItemRepository.deleteById(itemId);
    }
}