package com.itsconv.web.image.repository;

import com.itsconv.web.image.domain.PageImageMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PageImageMappingRepository extends JpaRepository<PageImageMapping, Long> {

    Optional<PageImageMapping> findBySlotId(Long slotId);
}
