package io.github.rurien.controller

import io.github.rurien.common.constant.Paths
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {
  @GetMapping(Paths.HealthCheck.HEALTH)
  fun healthCheck(): ResponseEntity<String> = ResponseEntity.ok("${HttpStatus.OK.value()} ${HttpStatus.OK.name}")
}
