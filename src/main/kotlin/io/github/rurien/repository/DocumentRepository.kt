package io.github.rurien.repository

import io.github.rurien.common.exception.DocumentNotFoundException
import org.springframework.stereotype.Repository
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Repository
class DocumentRepository {
  private val repository = ConcurrentHashMap<String, String>()

  fun save(text: String): String =
    UUID.randomUUID().toString().also {
      repository[it] = text
    }

  fun find(resumeId: String): String =
    repository[resumeId]
      ?: throw DocumentNotFoundException()

  fun delete(resumeId: String) {
    repository.remove(resumeId)
  }
}
