package io.github.rurien.model.request

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class OpenRouterChatRequest(
  val model: String,
  val messages: List<Message>,
) {
  data class Message(
    val role: String,
    val content: String,
  )

  enum class Role(
    private val roleName: String,
  ) {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant"),
    ;

    fun getRoleName(): String = this.roleName
  }
}
