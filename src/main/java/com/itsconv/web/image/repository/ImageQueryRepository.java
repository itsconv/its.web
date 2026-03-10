package com.itsconv.web.image.repository;

import com.itsconv.web.image.service.dto.view.ImageSlotCardView;

import java.util.List;
import java.util.Map;

public interface ImageQueryRepository {

    Map<Long, List<ImageSlotCardView>> findImageSlotCardsByMenuIds(List<Long> menuIds);
}
