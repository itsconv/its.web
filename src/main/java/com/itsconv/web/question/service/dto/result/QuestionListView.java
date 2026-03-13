package com.itsconv.web.question.service.dto.result;

import java.time.LocalDateTime;

import com.itsconv.web.question.domain.Question;

public record QuestionListView(
    Long id,
    String title,
    String createName,
    LocalDateTime createDate,
    String isCheck
) {
    public static QuestionListView from(Question q) {
        return new QuestionListView(
            q.getId(), q.getTitle(), q.getCreateName(), q.getCreateDate(), q.getIsCheck()
        );
    }
}
