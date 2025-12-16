package io.github.rurien.controller

import io.github.rurien.common.constant.Paths
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(Paths.HealthCheck.BASE)
class HealthCheckController {
  @Operation(summary = "서버 활성화 상태 체크")
  @GetMapping
  fun healthCheck(): ResponseEntity<String> = ResponseEntity.ok("${HttpStatus.OK.value()} ${HttpStatus.OK.name}")
}
