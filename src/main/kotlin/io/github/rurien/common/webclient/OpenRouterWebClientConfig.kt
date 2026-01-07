package io.github.rurien.common.webclient

import io.github.rurien.common.property.OpenRouterApiProperties
import io.netty.channel.ChannelOption
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Configuration
class OpenRouterWebClientConfig(
  private val openRouterApiProperties: OpenRouterApiProperties,
) {
  @Bean
  fun openRouterWebClient(builder: WebClient.Builder): WebClient =
    builder
      .baseUrl(openRouterApiProperties.baseUrl)
      .clientConnector(ReactorClientHttpConnector(httpClient()))
      .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .build()

  private fun httpClient(): HttpClient =
    HttpClient.create().apply {
      option(
        ChannelOption.CONNECT_TIMEOUT_MILLIS,
        openRouterApiProperties.http.timeout.connectMs
          .toInt(),
      )
      responseTimeout(Duration.ofMillis(openRouterApiProperties.http.timeout.responseMs))
    }
}
