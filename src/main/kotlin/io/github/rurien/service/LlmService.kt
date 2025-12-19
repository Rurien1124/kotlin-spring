package io.github.rurien.service

import io.github.rurien.communication.llm.GeminiCommunicator
import org.springframework.stereotype.Service

@Service
class LlmService(
  private val geminiCommunicator: GeminiCommunicator,
) {
  fun summary(chunks: List<String>): String =
    geminiCommunicator
      .summary(chunks)

  fun ask(
    context: String,
    question: String,
  ): String =
    geminiCommunicator
      .ask(
        context = context,
        question = question,
      )
}
