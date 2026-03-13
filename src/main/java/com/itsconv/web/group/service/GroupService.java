package com.itsconv.web.group.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itsconv.web.group.repository.GroupRepository;
import com.itsconv.web.group.service.dto.result.GroupListView;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    @Transactional(readOnly = true)
    public List<GroupListView> findGroupList() {
        return groupRepository.findAllByOrderBySortOrderAscIdAsc()
            .stream()
            .map(GroupListView::from)
            .toList();
    }
}
