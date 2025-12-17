package io.github.rurien.support

import org.springframework.stereotype.Component

@Component
class PrivacySanitizer {
  companion object {
    const val RRN_PATTERN = """\b\d{2}[.\-]?\d{2}[.\-]?\d{2}[-]?[1-4]\d{6}\b"""
    const val EMAIL_PATTERN = """[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}"""
    const val PHONE_PATTERN = """\b(01[016789]|02|0[3-9][0-9])[- ]?\d{3,4}[- ]?\d{4}\b"""
  }

  fun sanitize(raw: String): String =
    raw
      .normalizeLineBreaks()
      .removeExtraBlankLines()
      .maskResidentRegistrationNumber()
      .maskEmail()
      .maskPhone()
      .limitLength(30_000)

  private fun String.normalizeLineBreaks(): String = replace("\r\n", "\n").replace("\r", "\n")

  private fun String.removeExtraBlankLines(): String =
    lines()
      .map { it.trimEnd() }
      .fold(mutableListOf<String>()) { acc, line ->
        if (line.isNotBlank() || acc.lastOrNull()?.isNotBlank() == true) {
          acc.add(line)
        }
        acc
      }.joinToString("\n")

  /** 주민등록번호 마스킹 */
  private fun String.maskResidentRegistrationNumber(): String =
    replace(
      Regex(RRN_PATTERN),
      "[RRN]",
    )

  /** 이메일 마스킹 */
  private fun String.maskEmail(): String =
    replace(
      Regex(EMAIL_PATTERN),
      "[EMAIL]",
    )

  /** 휴대폰 번호 마스킹 */
  private fun String.maskPhone(): String =
    replace(
      Regex(PHONE_PATTERN),
      "[PHONE]",
    )

  private fun String.limitLength(max: Int): String = if (length <= max) this else substring(0, max)
}
