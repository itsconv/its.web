package com.itsconv.web.history.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itsconv.web.history.domain.History;
import com.itsconv.web.history.service.HistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping("/{parent}")
    public ResponseEntity<List<History>> getByParent(@PathVariable String parent) {
        return ResponseEntity.ok(historyService.findByParent(parent));
    }
}
