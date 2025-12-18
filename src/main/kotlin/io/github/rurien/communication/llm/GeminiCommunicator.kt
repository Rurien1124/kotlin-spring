package io.github.rurien.communication.llm

import com.google.genai.Client
import com.google.genai.types.GenerateContentConfig
import io.github.rurien.common.exception.GeminiResponseNullException
import io.github.rurien.common.property.GeminiProperties
import org.springframework.stereotype.Component

@Component
class GeminiCommunicator(
  private val geminiClient: Client,
  private val geminiProperties: GeminiProperties,
) : LlmCommunicator {
  override fun ask(
    chunks: List<String>,
    question: String,
  ): String =
    geminiClient.models
      .generateContent(
        geminiProperties.model,
        buildPrompt(chunks.toContext(), question),
        GenerateContentConfig.builder().build(),
      ).text()
      ?: throw GeminiResponseNullException()

  private fun buildPrompt(
    context: String,
    question: String,
  ): String =
    """
    당신은 문서 작성자 입장에서 질문에 대한 답변을 돕는 AI입니다.

    아래는 문서에서 추출한 문서입니다.
    문서에 없는 내용은 추측하지 마세요.

    [문서]
    $context

    [질문]
    $question

    문서 기반으로만 간결하게 답변하세요.
    """.trimIndent()
}
