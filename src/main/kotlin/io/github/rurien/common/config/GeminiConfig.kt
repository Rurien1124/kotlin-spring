package io.github.rurien.common.config

import com.google.genai.Client
import io.github.rurien.common.property.GeminiProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GeminiConfig(
  private val geminiProperties: GeminiProperties,
) {
  @Bean
  fun geminiClient(): Client =
    Client
      .builder()
      .apply {
        apiKey(geminiProperties.key)
      }.build()
}
