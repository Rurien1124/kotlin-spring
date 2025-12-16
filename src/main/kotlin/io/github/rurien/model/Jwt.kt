package io.github.rurien.model

import io.swagger.v3.oas.annotations.media.Schema

data class Jwt(
  @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOi ...", description = "JWT 문자열")
  val jwt: String,
  @Schema(example = "1735603200", description = "만료 시각 (Epoch second)")
  val expiresAt: String,
)
