package com.itsconv.web.question.domain;

import com.itsconv.web.common.domain.BaseTimeEntity;
import com.itsconv.web.question.controller.dto.request.QuestionCreateRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "its_question")
@Getter
@NoArgsConstructor
public class Question extends BaseTimeEntity{
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100)
    private String title;

    @Lob
    @Column(name = "contents", columnDefinition = "LONGTEXT")
    private String contents;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "is_check", nullable = false ,length = 1)
    private String isCheck = "N";

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "group_name", length = 20)
    private String groupName;

    @Column(name = "last_update_id", length = 50)
    private String lastUpdateId;

    @Column(name = "last_update_name", length = 20)
    private String lastUpdateName;

    @Column(name = "create_name", length = 20)
    private String createName;

    @Column(name = "del_yn", length = 1)
    private String delYn = "N";

    @Column(name = "sort_order")
    private Integer sortOrder;

    public void saveQuestion(QuestionCreateRequest req, int nextOrder) {
        this.title = req.title();
        this.contents = req.contents();
        this.email = req.email();
        this.groupId = req.groupId();
        this.groupName = req.groupName();
        this.createName = req.createName();
        this.delYn = "N";
        this.sortOrder = nextOrder;
    }

    public void updateCheck() {
        this.isCheck = "Y";
    }

    public void deleteQuestion() {
        this.delYn = "Y";
    }
}
