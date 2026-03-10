package com.itsconv.web.menu.repository;

import com.itsconv.web.menu.domain.Menu;
import com.itsconv.web.menu.domain.MenuDepth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByCode(String code);

    List<Menu> findByDepthAndUseYnOrderBySortOrderAsc(MenuDepth depth, String useYn);

    List<Menu> findByParentMenuIdAndUseYnOrderBySortOrderAsc(Long parentMenuId, String useYn);
}
