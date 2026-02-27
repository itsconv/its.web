package com.itsconv.web.history.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record HistoryTopModifyRequest(
   @NotBlank(message = "시작구간을 입력해주세요.")
   String start,

   @NotBlank(message = "종료구간을 입력해주세요.")
   String end,

   Long id
) {}
