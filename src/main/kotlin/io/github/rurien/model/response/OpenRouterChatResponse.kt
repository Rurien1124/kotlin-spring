package io.github.rurien.model.response

data class OpenRouterChatResponse(
  val choices: List<Choice>,
) {
  data class Choice(
    val message: Message,
  )

  data class Message(
    val role: String,
    val content: String,
  )
}
