package com.itsconv.web.menu.repository;

import com.itsconv.web.menu.domain.Menu;
import com.itsconv.web.menu.domain.MenuDepth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByCode(String code);

    @Query("SELECT m FROM Menu m WHERE m.depth = :depth AND m.useYn = 'Y' ORDER BY m.sortOrder ASC")
    List<Menu> findMenuByDepth(@Param("depth") MenuDepth depth);

    @Query("SELECT m FROM Menu m WHERE m.parentMenuId = :parentMenuId AND m.useYn = 'Y' ORDER BY m.sortOrder ASC")
    List<Menu> findMenuByParentMenuId(@Param("parentMenuId") Long parentMenuId);

    @Query("SELECT m FROM Menu m WHERE m.parentMenuId IN :parentMenuIds AND m.useYn = 'Y' ORDER BY m.sortOrder ASC")
    List<Menu> findMenuByParentMenuIdIn(@Param("parentMenuIds") List<Long> parentMenuIds);
}
