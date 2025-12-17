package io.github.rurien.controller

import io.github.rurien.common.constant.Paths
import io.github.rurien.model.Document
import io.github.rurien.service.DocumentService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(Paths.Document.BASE)
class DocumentController(
  val documentService: DocumentService,
) {
  @PostMapping(
    consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE],
  )
  fun upload(
    @RequestPart("file") file: MultipartFile,
  ): Document =
    Document(
      texts = documentService.extractText(file),
    )
}
