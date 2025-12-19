package io.github.rurien.model

import io.swagger.v3.oas.annotations.media.Schema

data class DocumentResponse(
  @Schema(example = "12ee034d-9e5a-4a37-b8f9-22147cabeea1", description = "문서 ID")
  val documentId: String,
  @Schema(example = "요약 문서 데이터 ...", description = "LLM 으로 요약된 문서 데이터")
  val text: String,
)
