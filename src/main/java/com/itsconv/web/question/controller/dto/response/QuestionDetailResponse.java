package com.itsconv.web.question.controller.dto.response;

import java.time.LocalDateTime;

import com.itsconv.web.question.domain.Question;
import com.itsconv.web.question.service.dto.result.QuestionView;

public record QuestionDetailResponse(
    Long id,
    String createName,
    LocalDateTime createDate,
    LocalDateTime lastUpdate,
    String email,
    String title,
    String contents,
    String groupName,
    QuestionView prevPost,
    QuestionView nextPost
) {
    public static QuestionDetailResponse from(Question q, QuestionView prev, QuestionView next) {
        return new QuestionDetailResponse(
            q.getId(), 
            q.getCreateName(), 
            q.getCreateDate(),
            q.getLastUpdate(),
            q.getEmail(), 
            q.getTitle(), 
            q.getContents(), 
            q.getGroupName(), 
            prev, 
            next
        );
    }
}
