package com.itsconv.web.image.repository.impl;

import com.itsconv.web.file.domain.QFile;
import com.itsconv.web.image.domain.QPageImageMapping;
import com.itsconv.web.image.domain.QPageImageSlot;
import com.itsconv.web.image.repository.ImageQueryRepository;
import com.itsconv.web.image.repository.dto.ImageSlotCardRow;
import com.itsconv.web.image.service.dto.view.ImageSlotCardView;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ImageQueryRepositoryImpl implements ImageQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Map<Long, List<ImageSlotCardView>> findImageSlotCardsByMenuIds(List<Long> menuIds) {
        return findSlotCardRowsByMenuIds(menuIds).stream()
                .collect(Collectors.groupingBy(
                        ImageSlotCardRow::menuId,
                        Collectors.mapping(ImageSlotCardRow::toView, Collectors.toList())
                ));
    }

    private List<ImageSlotCardRow> findSlotCardRowsByMenuIds(List<Long> menuIds) {
        QPageImageSlot slot = QPageImageSlot.pageImageSlot;
        QPageImageMapping mapping = QPageImageMapping.pageImageMapping;
        QFile file = QFile.file;

        return queryFactory
                .select(Projections.constructor(
                        ImageSlotCardRow.class,
                        slot.tabMenuId,
                        slot.id,
                        slot.code,
                        slot.name,
                        slot.guideText,
                        slot.guideWidth,
                        slot.guideHeight,
                        mapping.fileId,
                        file.path,
                        file.originName
                ))
                .from(slot)
                .leftJoin(mapping).on(mapping.slotId.eq(slot.id))
                .leftJoin(file).on(file.id.eq(mapping.fileId))
                .where(slot.tabMenuId.in(menuIds))
                .orderBy(slot.tabMenuId.asc(), slot.id.asc())
                .fetch();
    }
}
