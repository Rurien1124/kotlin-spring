package io.github.rurien.filter

import io.github.rurien.common.security.JwtProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
  private val jwtProvider: JwtProvider,
) : OncePerRequestFilter() {
  private companion object {
    const val BEARER_PREFIX = "Bearer "
  }

  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filterChain: FilterChain,
  ) {
    resolveJwt(request)
      ?.takeIf(jwtProvider::validate)
      ?.let(jwtProvider::getAuthentication)
      ?.authenticate()

    filterChain.doFilter(request, response)
  }

  private fun resolveJwt(request: HttpServletRequest): String? =
    request
      .getHeader(HttpHeaders.AUTHORIZATION)
      ?.takeIf { it.startsWith(BEARER_PREFIX, ignoreCase = true) }
      ?.removePrefix(BEARER_PREFIX)
      ?.takeIf { it.isNotBlank() }

  private fun Authentication.authenticate() {
    SecurityContextHolder.getContext().authentication = this
  }
}
