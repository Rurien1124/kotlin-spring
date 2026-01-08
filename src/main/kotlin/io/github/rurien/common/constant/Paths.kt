package io.github.rurien.common.constant

object Paths {
  object HealthCheck {
    const val BASE = "/health"
  }

  object Token {
    const val BASE = "/token"
  }

  object Document {
    const val BASE = "/document"

    const val GET = "/{documentId}"
  }

  object Llm {
    const val BASE = "/llm"

    const val POST_QUESTION = "/{documentId}/question"
  }

  object OpenRouter {
    const val CHAT_COMPLETIONS = "/chat/completions"
  }
}
