package io.github.rurien.service

import io.github.rurien.common.exception.NonTextDocumentException
import io.github.rurien.common.exception.UnsupportedDocumentException
import io.github.rurien.support.DocumentTextExtractor
import io.github.rurien.support.PrivacySanitizer
import io.github.rurien.support.TextChunker
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class DocumentService(
  private val extractors: List<DocumentTextExtractor>,
  private val privacySanitizer: PrivacySanitizer,
  private val textChunker: TextChunker,
) {
  fun extractText(file: MultipartFile): List<String> {
    val extractor =
      extractors.firstOrNull { it.supports(file.contentType.orEmpty()) }
        ?: throw UnsupportedDocumentException()

    return extractor
      .extract(file.inputStream)
      .let(privacySanitizer::sanitize)
      .also { text ->
        if (text.length < 200) {
          throw NonTextDocumentException()
        }
      }.let(textChunker::chunk)
  }
}
