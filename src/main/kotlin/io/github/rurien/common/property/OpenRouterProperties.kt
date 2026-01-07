package io.github.rurien.common.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "open-router.api")
data class OpenRouterProperties(
  val key: String,
  val models: OpenRouterModels,
)

data class OpenRouterModels(
  val nvidiaNemotron: String,
)
