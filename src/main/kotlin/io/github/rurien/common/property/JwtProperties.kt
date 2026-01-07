package io.github.rurien.common.property

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
  val enabled: Boolean,
  val secret: String,
  val accessToken: AccessToken,
  val refreshToken: RefreshToken,
)

data class AccessToken(
  val expiration: Duration,
)

data class RefreshToken(
  val expiration: Duration,
)
