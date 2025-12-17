package io.github.rurien.model

import io.github.rurien.model.response.JwtResponse

data class Jwt(
  val jwt: String,
  val expiresAt: String,
) {
  fun toResponse(): JwtResponse =
    JwtResponse(
      jwt = jwt,
      expiresAt = expiresAt,
    )
}
