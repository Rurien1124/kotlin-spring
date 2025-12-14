package io.github.rurien.common.security

import io.github.rurien.common.property.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import javax.crypto.SecretKey

@Component
class JwtProvider(
  private val jwtProperties: JwtProperties,
) {
  private companion object {
    const val CLAIMS_TYPE = "CLIENT"
    const val CLAIMS_ROLE = "PUBLIC"
  }

  private val signingKey: SecretKey =
    Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray(StandardCharsets.UTF_8))

  private val accessTokenExpiration: Duration = jwtProperties.accessToken.expiration

  fun create(): String {
    val now = LocalDateTime.now()
    val issuedAt =
      now
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .epochSecond
    val expirationTime =
      now
        .plus(accessTokenExpiration)
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .epochSecond

    return Jwts
      .builder()
      .subject("client")
      .claim("type", CLAIMS_TYPE)
      .claim("role", CLAIMS_ROLE)
      .claim("iat", issuedAt)
      .claim("exp", expirationTime)
      .signWith(signingKey)
      .compact()
  }

  fun validate(jwt: String): Boolean =
    runCatching {
      Jwts
        .parser()
        .verifyWith(signingKey)
        .build()
        .parseSignedClaims(jwt)

      true
    }.getOrElse { false }

  fun getAuthentication(jwt: String): Authentication {
    val claims =
      Jwts
        .parser()
        .verifyWith(signingKey)
        .build()
        .parseSignedClaims(jwt)
        .payload

    require(claims["type"] == CLAIMS_TYPE)

    val principal =
      ClientPrincipal(
        clientId = claims.subject,
      )

    return UsernamePasswordAuthenticationToken(
      principal,
      null,
      listOf(SimpleGrantedAuthority("ROLE_PUBLIC")),
    )
  }
}
