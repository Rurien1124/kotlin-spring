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
  private val llmService: LlmService,
  private val extractors: List<DocumentTextExtractor>,
  private val privacySanitizer: PrivacySanitizer,
  private val textChunker: TextChunker,
) {
  fun upload(file: MultipartFile): DocumentResponse =
    extractText(file)
      .let { chunks -> llmService.summary(chunks) }
      .let { context ->
        DocumentResponse(
          documentId = saveText(context),
          text = context,
        )
      }

  fun find(documentId: String): String = documentRepository.find(documentId)

  fun ask(
    documentId: String,
    question: String,
  ): String =
    find(documentId)
      .let { context ->
        llmService.ask(context, question)
      }

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

  private fun saveText(text: String): String = documentRepository.save(text)
}
