package io.github.rurien.communication.llm

import com.google.genai.Client
import com.google.genai.types.GenerateContentConfig
import io.github.rurien.common.constant.Prompts
import io.github.rurien.common.exception.GeminiResponseNullException
import io.github.rurien.common.property.GeminiProperties
import org.springframework.stereotype.Component

@Component
class GeminiCommunicator(
  private val geminiClient: Client,
  private val geminiProperties: GeminiProperties,
  private val geminiSummaryContentConfig: GenerateContentConfig,
  private val geminiAskContentConfig: GenerateContentConfig,
) : LlmCommunicator {
  override fun summary(chunks: List<String>): String =
    generateContent(
      prompt = buildPrompt(Prompts.SUMMARY_PROMPT, chunks.toContext()),
      model = geminiProperties.models.flash,
      generateContentConfig = geminiSummaryContentConfig,
    )

  override fun ask(
    context: String,
    question: String,
  ): String =
    generateContent(
      prompt = buildPrompt(Prompts.ASK_PROMPT, context, question),
      model = geminiProperties.models.flashLite,
      generateContentConfig = geminiAskContentConfig,
    )

  private fun generateContent(
    prompt: String,
    model: String,
    generateContentConfig: GenerateContentConfig,
  ): String =
    geminiClient.models
      .generateContent(
        model,
        prompt,
        generateContentConfig,
      ).text()
      ?: throw GeminiResponseNullException()

  private fun buildPrompt(
    prompt: String,
    context: String,
    question: String? = null,
  ): String =
    """
    $prompt

    [문서]
    $context

    ${question?.let { "\n[질문]\n$it" }.orEmpty()}
    """.trimMargin()
}
