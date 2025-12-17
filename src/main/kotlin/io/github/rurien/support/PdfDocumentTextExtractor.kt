package io.github.rurien.support

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class PdfDocumentTextExtractor : DocumentTextExtractor {
  override fun supports(contentType: String): Boolean = contentType == MediaType.APPLICATION_PDF_VALUE

  override fun extract(inputStream: InputStream): String =
    PDDocument.load(inputStream).use { document ->
      PDFTextStripper()
        .run {
          sortByPosition = true
          getText(document)
        }.trim()
    }
}
