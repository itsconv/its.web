package com.itsconv.web.image.repository.impl;

import com.itsconv.web.file.domain.QFile;
import com.itsconv.web.image.domain.QPageImageMapping;
import com.itsconv.web.image.domain.QPageImageSlot;
import com.itsconv.web.image.repository.ImageQueryRepository;
import com.itsconv.web.image.service.dto.view.ImageSlotCardView;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageQueryRepositoryImpl implements ImageQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ImageSlotCardView> findImageSlotCardsByMenuId(Long menuId) {
        QPageImageSlot slot = QPageImageSlot.pageImageSlot;
        QPageImageMapping mapping = QPageImageMapping.pageImageMapping;
        QFile file = QFile.file;

        return queryFactory
                .select(Projections.constructor(
                        ImageSlotCardView.class,
                        slot.id,
                        slot.code,
                        slot.name,
                        slot.guideText,
                        slot.guideSize,
                        mapping.fileId,
                        file.path,
                        file.originName
                ))
                .from(slot)
                .leftJoin(mapping).on(mapping.slotId.eq(slot.id))
                .leftJoin(file).on(file.id.eq(mapping.fileId))
                .where(slot.tabMenuId.eq(menuId))
                .orderBy(slot.id.asc())
                .fetch();
    }
}
