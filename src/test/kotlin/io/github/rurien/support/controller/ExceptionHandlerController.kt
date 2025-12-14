package io.github.rurien.support.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/exception-handler")
class ExceptionHandlerController {
  @GetMapping("/unexpected-exception")
  fun unexpectedException(): String = throw Exception("Unexpected exception")
}
