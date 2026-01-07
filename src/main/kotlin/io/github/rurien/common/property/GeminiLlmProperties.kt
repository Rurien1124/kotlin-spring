package io.github.rurien.common.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "gemini")
data class GeminiProperties(
  val key: String,
  val models: GeminiModels,
)

data class GeminiModels(
  val flash: String,
  val flashLite: String,
)
