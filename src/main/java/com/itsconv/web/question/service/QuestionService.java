package com.itsconv.web.question.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itsconv.web.common.exception.BusinessException;
import com.itsconv.web.common.exception.ErrorCode;
import com.itsconv.web.question.controller.dto.request.QuestionCreateRequest;
import com.itsconv.web.question.controller.dto.response.QuestionDetailResponse;
import com.itsconv.web.question.domain.Question;
import com.itsconv.web.question.repository.QuestionRepository;
import com.itsconv.web.question.service.dto.result.QuestionListView;
import com.itsconv.web.question.service.dto.result.QuestionView;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public Page<QuestionListView> findQuestionList(int page, int size) {
        int safePage = Math.max(1, page) - 1;
        int safeSize = Math.max(1, Math.min(size, 100));
        
        Pageable pageable = PageRequest.of(safePage, safeSize);

        return questionRepository.findQuestionList(pageable).map(m -> QuestionListView.from(m));
    }

    @Transactional
    public QuestionDetailResponse findQuestionOneById(Long id) {
        Question question = questionRepository.findByIdAndDelYn(id, "N")
            .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_NOT_FOUND));

        if ("N".equals(question.getIsCheck())) {
            question.updateCheck();
        }

        QuestionView prevPost = questionRepository.findPrevByIdAndOrder(question.getId(), question.getSortOrder())
            .map(m -> QuestionView.from(m))
            .orElse(null);

        QuestionView nextPost = questionRepository.findNextByIdAndOrder(question.getId(), question.getSortOrder())
            .map(m -> QuestionView.from(m))
            .orElse(null);

        return QuestionDetailResponse.from(question, prevPost, nextPost);
    }

    @Transactional
    public void registQuestion(QuestionCreateRequest request) {
        Question question = new Question();

        int nextOrder = questionRepository.findMaxOrder() + 1;

        question.saveQuestion(request, nextOrder);

        questionRepository.save(question);
    }

    @Transactional
    public void deleteQuestion(Long id) {
        // lastUpdate 갱신하지 않고 논리삭제
        questionRepository.softDeleteWithoutAudit(id);
    }
}
