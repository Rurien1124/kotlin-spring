package io.github.rurien.support

import java.io.InputStream

interface DocumentTextExtractor {
  fun supports(contentType: String): Boolean

  fun extract(inputStream: InputStream): String
}
