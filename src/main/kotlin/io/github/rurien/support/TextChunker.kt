package io.github.rurien.support

import org.springframework.stereotype.Component

@Component
class TextChunker(
  private val maxLength: Int = 1_000,
  private val minLength: Int = 200,
) {
  fun chunk(text: String): List<String> {
    if (text.length <= maxLength) {
      return listOf(text)
    }

    return text
      .splitByParagraph()
      .flatMap(::splitIfTooLong)
      .mergeByLength(maxLength)
      .filter { it.length >= minLength }
  }

  private fun String.splitByParagraph(): List<String> = split("\n\n").map(String::trim).filter(String::isNotBlank)

  private fun splitIfTooLong(paragraph: String): List<String> =
    if (paragraph.length <= maxLength) {
      listOf(paragraph)
    } else {
      paragraph.chunked(maxLength)
    }

  private fun List<String>.mergeByLength(maxLength: Int): List<String> {
    if (isEmpty()) return emptyList()

    val result = mutableListOf<String>()
    var buffer = StringBuilder()

    forEach { part ->
      if (buffer.length + part.length + 2 <= maxLength) {
        if (buffer.isNotEmpty()) buffer.append("\n\n")
        buffer.append(part)
      } else {
        result += buffer.toString()
        buffer = StringBuilder(part)
      }
    }

    result += buffer.toString()
    return result
  }
}
