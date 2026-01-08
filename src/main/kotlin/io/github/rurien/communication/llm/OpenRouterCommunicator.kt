package io.github.rurien.communication.llm

import io.github.rurien.common.constant.Paths
import io.github.rurien.common.constant.Prompts
import io.github.rurien.common.exception.OpenRouterResponseNullException
import io.github.rurien.common.property.OpenRouterProperties
import io.github.rurien.model.request.OpenRouterChatRequest
import io.github.rurien.model.response.OpenRouterChatResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class OpenRouterCommunicator(
  private val webClient: WebClient,
  private val openRouterProperties: OpenRouterProperties,
) : LlmCommunicator {
  override fun summary(chunks: List<String>): String =
    call(
      model = openRouterProperties.models.nvidiaNemotron,
      prompt = buildPrompt(Prompts.SUMMARY_PROMPT, chunks.joinToString("\n")),
    )

  override fun ask(
    context: String,
    question: String,
  ): String =
    call(
      model = openRouterProperties.models.nvidiaNemotron,
      prompt = buildPrompt(Prompts.ASK_PROMPT, context, question),
    )

  private fun call(
    model: String,
    prompt: String,
  ): String {
    val request =
      OpenRouterChatRequest(
        model = model,
        messages =
          listOf(
            OpenRouterChatRequest.Message(
              role = OpenRouterChatRequest.Role.USER.getRoleName(),
              content = prompt,
            ),
          ),
      )

    return webClient
      .post()
      .uri(Paths.OpenRouter.CHAT_COMPLETIONS)
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .bodyValue(request)
      .retrieve()
      .bodyToMono(OpenRouterChatResponse::class.java)
      .map {
        it.choices
          .firstOrNull()
          ?.message
          ?.content
          ?: throw OpenRouterResponseNullException()
      }.block()!!
  }

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
    """.trimIndent()
}
