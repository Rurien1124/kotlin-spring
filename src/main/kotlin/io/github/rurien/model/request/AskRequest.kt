package io.github.rurien.model.request

import io.swagger.v3.oas.annotations.media.Schema

data class AskRequest(
  @Schema(example = "질문을 입력해 주세요", description = "LLM 질문")
  val question: String,
)
