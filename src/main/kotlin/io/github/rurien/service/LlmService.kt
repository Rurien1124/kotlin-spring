package io.github.rurien.service

import io.github.rurien.communication.llm.OpenRouterCommunicator
import org.springframework.stereotype.Service

@Service
class LlmService(
  private val openRouterCommunicator: OpenRouterCommunicator,
) {
  fun summary(chunks: List<String>): String =
    openRouterCommunicator
      .summary(chunks)

  fun ask(
    context: String,
    question: String,
  ): String =
    openRouterCommunicator
      .ask(
        context = context,
        question = question,
      )
}
