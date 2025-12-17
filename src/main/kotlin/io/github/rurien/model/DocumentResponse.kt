package io.github.rurien.model

import io.swagger.v3.oas.annotations.media.Schema

data class DocumentResponse(
  @Schema(example = "12ee034d-9e5a-4a37-b8f9-22147cabeea1", description = "문서 ID")
  val documentId: String,
  @Schema(
    example = """
    "문서 샘플 데이터",
    "Chunked text data",
  """,
    description = "Chunked 문서",
  )
  val texts: List<String>,
)
