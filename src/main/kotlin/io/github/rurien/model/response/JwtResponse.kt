package io.github.rurien.model.response

import io.swagger.v3.oas.annotations.media.Schema

data class JwtResponse(
  @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOi ...", description = "JWT 문자열")
  val jwt: String,
  @Schema(example = "1735603200", description = "만료 시각 (Epoch second)")
  val expiresAt: String,
)
