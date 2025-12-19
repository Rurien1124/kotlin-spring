package io.github.rurien.communication.llm

interface LlmCommunicator {
  /** 텍스트 기반 문서 요약 */
  fun summary(chunks: List<String>): String

  /** 텍스트 기반 문서 질문 */
  fun ask(
    context: String,
    question: String,
  ): String

  fun List<String>.toContext() = joinToString("\n\n")
}
