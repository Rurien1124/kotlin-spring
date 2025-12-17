package io.github.rurien.support

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class PrivacySanitizerTest {
  private val privacySanitizer = PrivacySanitizer()

  @Test
  @DisplayName("주민등록번호는 [RRN]으로 마스킹된다")
  fun maskResidentRegistrationNumber() {
    // Given
    val input =
      """
      이름: 홍길동
      주민등록번호: 900101-1234567
      """.trimIndent()

    // When
    val result = privacySanitizer.sanitize(input)

    // Then
    assertEquals(
      """
      이름: 홍길동
      주민등록번호: [RRN]
      """.trimIndent(),
      result,
    )
  }

  @Test
  @DisplayName("이메일 주소는 [EMAIL]로 마스킹된다")
  fun maskEmail() {
    // Given
    val input =
      """
      이메일: test.user+dev@gmail.com
      """.trimIndent()

    // When
    val result = privacySanitizer.sanitize(input)

    // Then
    assertEquals(
      """
      이메일: [EMAIL]
      """.trimIndent(),
      result,
    )
  }

  @Test
  @DisplayName("전화번호는 [PHONE]으로 마스킹된다")
  fun maskPhone() {
    // Given
    val input =
      """
      연락처: 010-1234-5678
      """.trimIndent()

    // When
    val result = privacySanitizer.sanitize(input)

    // Then
    assertEquals(
      """
      연락처: [PHONE]
      """.trimIndent(),
      result,
    )
  }

  @Test
  @DisplayName("여러 개인정보가 동시에 마스킹된다")
  fun sanitize() {
    // Given
    val input =
      """
      주민등록번호: 9001011234567
      이메일: dev@test.co.kr
      전화번호: 02-123-4567
      """.trimIndent()

    // When
    val result = privacySanitizer.sanitize(input)

    // Then
    assertEquals(
      """
      주민등록번호: [RRN]
      이메일: [EMAIL]
      전화번호: [PHONE]
      """.trimIndent(),
      result,
    )
  }

  @Test
  @DisplayName("불필요한 빈 줄은 하나로 정리된다")
  fun removeExtraBlankLines() {
    // Given
    val input =
      """
      경력 사항


      - Java 개발


      - Spring Boot
      """.trimIndent()

    // When
    val result = privacySanitizer.sanitize(input)

    // Then
    assertEquals(
      """
      경력 사항

      - Java 개발

      - Spring Boot
      """.trimIndent(),
      result,
    )
  }

  @Test
  @DisplayName("텍스트 길이는 최대 30,000자로 제한된다")
  fun limitLength() {
    // Given
    val input = "a".repeat(40_000)

    // When
    val result = privacySanitizer.sanitize(input)

    // Then
    assertEquals(30_000, result.length)
  }
}
