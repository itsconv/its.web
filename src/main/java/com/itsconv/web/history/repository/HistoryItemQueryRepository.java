package com.itsconv.web.history.repository;

import java.util.List;

import com.itsconv.web.history.service.dto.result.HistoryItemListView;

public interface HistoryItemQueryRepository {
    List<HistoryItemListView> findYearItemByPeriodId(Long periodId);
}
