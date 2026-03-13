package com.itsconv.web.group.service.dto.result;

import com.itsconv.web.group.domain.Group;

public record GroupListView(
    Long id,
    String name
) {
    public static GroupListView from(Group group) {
        return new GroupListView(group.getId(), group.getName());
    }
}
