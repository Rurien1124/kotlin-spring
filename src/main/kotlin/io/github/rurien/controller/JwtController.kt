package io.github.rurien.controller

import io.github.rurien.common.config.NoSecurityRequirement
import io.github.rurien.common.constant.Paths
import io.github.rurien.common.security.JwtProvider
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(Paths.Token.BASE)
class JwtController(
  private val jwtProvider: JwtProvider,
) {
  @NoSecurityRequirement
  @Operation(
    summary = "JWT 토큰 발급",
  )
  @PostMapping
  fun issue(): Map<String, String> = mapOf("token" to jwtProvider.create())
}
