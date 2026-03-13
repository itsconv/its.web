package com.itsconv.web.question.service.dto.result;

import com.itsconv.web.question.domain.Question;

public record QuestionView(
    Long id,
    String title
) {
    public static QuestionView from (Question q) {
        return new QuestionView(
            q.getId(),
            q.getTitle()
        );
    }
}
