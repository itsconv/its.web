package com.itsconv.web.group.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itsconv.web.common.response.ApiResponse;
import com.itsconv.web.group.service.GroupService;
import com.itsconv.web.group.service.dto.result.GroupListView;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<GroupListView>>> getGroupList() {
        return ResponseEntity.ok(ApiResponse.success(groupService.findGroupList()));
    }
}
