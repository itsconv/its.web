package com.itsconv.web.board.controller.dto.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.itsconv.web.board.domain.BoardSearchType;
import com.itsconv.web.board.domain.BoardType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardListRequest {
    private BoardType type;
    private String keyword = "";
    private BoardSearchType searchType = BoardSearchType.TITLE;
    private int page = 1;
    private int size = 10;

    public Pageable toPageable() {
        int safePage = Math.max(1, page) - 1;
        int safeSize = Math.max(1, Math.min(size, 100));
        return PageRequest.of(safePage, safeSize, Sort.by(Sort.Direction.DESC, "id"));
    }

    public String avoidNullKeyword() {
        return keyword == null ? "" : keyword.trim();
    }
}
