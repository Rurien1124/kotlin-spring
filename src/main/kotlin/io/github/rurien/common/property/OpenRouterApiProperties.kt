package io.github.rurien.common.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "open-router.api")
data class OpenRouterApiProperties(
  val baseUrl: String,
  val http: Http = Http(),
) {
  data class Http(
    val timeout: Timeout = Timeout(),
    val retry: Retry = Retry(),
  )

  data class Timeout(
    val connectMs: Long = 3000,
    val responseMs: Long = 30000,
  )

  data class Retry(
    val maxAttempts: Int = 2,
    val backoffMs: Long = 500,
  )
}
