package io.github.rurien.service

import io.github.rurien.common.exception.NonTextDocumentException
import io.github.rurien.common.exception.UnsupportedDocumentException
import io.github.rurien.model.DocumentResponse
import io.github.rurien.repository.DocumentRepository
import io.github.rurien.support.DocumentTextExtractor
import io.github.rurien.support.PrivacySanitizer
import io.github.rurien.support.TextChunker
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class DocumentService(
  private val documentRepository: DocumentRepository,
  private val extractors: List<DocumentTextExtractor>,
  private val privacySanitizer: PrivacySanitizer,
  private val textChunker: TextChunker,
) {
  fun upload(file: MultipartFile): DocumentResponse =
    extractText(file).let { texts ->
      DocumentResponse(
        documentId = saveText(texts),
        texts = texts,
      )
    }

  fun find(documentId: String): List<String> = documentRepository.find(documentId)

  private fun extractText(file: MultipartFile): List<String> {
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

  private fun saveText(text: List<String>): String = documentRepository.save(text)
}
