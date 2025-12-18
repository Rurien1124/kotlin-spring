package io.github.rurien.common.config

import com.google.genai.Client
import com.google.genai.types.GenerateContentConfig
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

  @Bean
  fun geminiContentConfig(): GenerateContentConfig =
    GenerateContentConfig
      .builder()
      .temperature(0.2f) // 환각 방지를 위해 창의성 낮게 설정
      .maxOutputTokens(50)
      .build()
}
