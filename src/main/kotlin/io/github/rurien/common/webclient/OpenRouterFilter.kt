package io.github.rurien.common.webclient

import io.github.rurien.common.property.OpenRouterProperties
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import reactor.core.publisher.Mono

@Configuration
class OpenRouterFilter(
  private val openRouterProperties: OpenRouterProperties,
) {
  private val log = KotlinLogging.logger {}

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

  @Bean
  fun openRouterRequestLoggingFilter(): ExchangeFilterFunction =
    ExchangeFilterFunction.ofRequestProcessor { request ->
      log.info("=== Request Details ===")
      log.info("URI: ${request.url()}")
      log.info("Method: ${request.method()}")
      log.info("Headers: ${request.headers()}")

      Mono.just(request)
    }

  @Bean
  fun openRouterResponseLoggingFilter(): ExchangeFilterFunction =
    ExchangeFilterFunction.ofResponseProcessor { response ->
      response
        .bodyToMono(String::class.java)
        .defaultIfEmpty("")
        .flatMap { body ->
          log.info("=== Response Details ===")
          log.info("Status: ${response.statusCode()}")
          log.info("Headers: ${response.headers().asHttpHeaders()}")
          log.info("Body: $body")

          Mono.just(
            ClientResponse
              .create(response.statusCode())
              .headers { it.addAll(response.headers().asHttpHeaders()) }
              .body(body)
              .build(),
          )
        }
    }
}
