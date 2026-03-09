package com.itsconv.web.image.repository;

import com.itsconv.web.image.service.dto.view.ImageSlotCardView;

import java.util.List;

public interface ImageQueryRepository {

    List<ImageSlotCardView> findImageSlotCardsByMenuId(Long menuId);
}
