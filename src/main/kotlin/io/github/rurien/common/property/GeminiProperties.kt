package io.github.rurien.common.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "gemini.api")
data class GeminiProperties(
  val key: String,
  val model: String,
)
