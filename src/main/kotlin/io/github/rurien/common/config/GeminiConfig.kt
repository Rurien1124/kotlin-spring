package io.github.rurien.common.config

import com.google.genai.Client
import com.google.genai.types.Content
import com.google.genai.types.GenerateContentConfig
import com.google.genai.types.Part
import io.github.rurien.common.property.GeminiProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GeminiConfig(
  private val geminiProperties: GeminiProperties,
) {
  @Bean
  fun geminiAskClient(): Client =
    Client
      .builder()
      .apply {
        apiKey(geminiProperties.key)
      }.build()

  @Bean
  fun geminiSummaryContentConfig(): GenerateContentConfig =
    GenerateContentConfig
      .builder()
      .temperature(0.1f) // 환각 방지를 위해 창의성 낮게 설정
      .maxOutputTokens(8192) // 요약 잘림 방지를 위해 최대치로 설정
      .systemInstruction(
        Content.fromParts(
          Part.fromText(
            """
            당신은 긴 문서를 누락 없이 요약하는 전문가입니다.
            중간에 생략하거나 멈추지 말고 마지막 문장까지 상세히 요약하세요.
            """,
          ),
        ),
      ).build()

  @Bean
  fun geminiAskContentConfig(): GenerateContentConfig =
    GenerateContentConfig
      .builder()
      .temperature(0.1f)
      .maxOutputTokens(250)
      .systemInstruction(
        Content.fromParts(
          Part.fromText(
            """
            당신은 제공된 문서를 바탕으로 사용자와 대화하는 전문 가이드입니다.
            문서에 없는 내용은 절대 추측하지 마세요.
            """,
          ),
        ),
      ).build()
}
