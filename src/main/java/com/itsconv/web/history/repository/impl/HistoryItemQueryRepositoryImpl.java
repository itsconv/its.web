package com.itsconv.web.history.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itsconv.web.history.domain.QHistoryItem;
import com.itsconv.web.history.domain.QHistoryYear;
import com.itsconv.web.history.repository.HistoryItemQueryRepository;
import com.itsconv.web.history.service.dto.result.HistoryItemListView;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HistoryItemQueryRepositoryImpl implements HistoryItemQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<HistoryItemListView> findYearItemByPeriodId(Long periodId) {
        QHistoryYear year = QHistoryYear.historyYear;
        QHistoryItem item = QHistoryItem.historyItem;

        return queryFactory
            .select(Projections.constructor(
                HistoryItemListView.class,
                year.id,
                year.year,
                year.sortOrder,
                item.id,
                item.content,
                item.sortOrder
            ))
            .from(year)
            .leftJoin(item).on(item.year.eq(year))
            .where(year.period.id.eq(periodId))
            .orderBy(year.sortOrder.desc(), item.sortOrder.asc())
            .fetch();
    }
}
