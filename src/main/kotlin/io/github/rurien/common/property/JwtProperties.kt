package io.github.rurien.common.property

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "jwt")
class JwtProperties(
  val secret: String,
  val accessToken: AccessToken,
  val refreshToken: RefreshToken,
) {
  class AccessToken(
    val expiration: Duration,
  )

  class RefreshToken(
    val expiration: Duration,
  )
}
