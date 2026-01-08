package io.github.rurien.common.webclient

import io.github.rurien.common.property.OpenRouterProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import reactor.core.publisher.Mono

@Configuration
class OpenRouterFilter(
  private val openRouterProperties: OpenRouterProperties,
) {
  @Bean
  fun openRouterAuthFilter(): ExchangeFilterFunction =
    ExchangeFilterFunction.ofRequestProcessor { request ->
      Mono.just(
        ClientRequest
          .from(request)
          .header(
            HttpHeaders.AUTHORIZATION,
            "Bearer ${openRouterProperties.key}",
          ).build(),
      )
    }
}
