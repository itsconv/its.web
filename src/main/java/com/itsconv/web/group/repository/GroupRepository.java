package com.itsconv.web.group.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itsconv.web.group.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByOrderBySortOrderAscIdAsc();
}
