package com.itsconv.web.question.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record QuestionCreateRequest(
    @NotBlank(message = "제목을 입력해주세요")
    String title,

    @NotBlank(message = "내용을 입력해주세요")
    String contents,

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식이 올바르지 않습니다")
    @Pattern(
        regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
        message = "이메일 형식이 올바르지 않습니다"
    )
    String email,

    String createName,

    @NotNull(message = "담당부서를 선택해주세요")
    Long groupId,

    String groupName
) {
    
}
