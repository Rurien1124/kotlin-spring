package io.github.rurien.model.response

import io.swagger.v3.oas.annotations.media.Schema

data class AskResponse(
  @Schema(example = "질문에 대한 응답 문자열", description = "LLM 응답")
  val answer: String,
)
