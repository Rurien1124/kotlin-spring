package io.github.rurien.service

import io.github.rurien.communication.llm.GeminiCommunicator
import org.springframework.stereotype.Service

@Service
class LlmService(
  private val geminiCommunicator: GeminiCommunicator,
  private val documentService: DocumentService,
) {
  fun ask(
    documentId: String,
    question: String,
  ): String =
    geminiCommunicator
      .ask(
        chunks = documentService.find(documentId),
        question = question,
      )
}
