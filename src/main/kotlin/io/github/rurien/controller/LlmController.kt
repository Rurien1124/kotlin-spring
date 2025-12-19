package io.github.rurien.controller

import io.github.rurien.common.constant.Paths
import io.github.rurien.model.request.AskRequest
import io.github.rurien.model.response.AskResponse
import io.github.rurien.service.DocumentService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(Paths.Llm.BASE)
class LlmController(
  private val documentService: DocumentService,
) {
  @PostMapping(Paths.Llm.POST_QUESTION)
  fun question(
    @PathVariable documentId: String,
    @RequestBody request: AskRequest,
  ): AskResponse =
    AskResponse(
      documentService.ask(documentId, request.question),
    )
}
