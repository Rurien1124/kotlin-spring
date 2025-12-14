package io.github.rurien.common.config

import io.github.rurien.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.time.Duration

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
  private val jwtAuthenticationFilter: JwtAuthenticationFilter,
) {
  private companion object {
    // TODO: 프론트엔드 도메인 설정 필요
    private val ALLOWED_ORIGINS =
      listOf(
        "http://localhost:3000",
        "http://myfrontend.com",
      )

    private val ALLOWED_METHODS =
      listOf(
        HttpMethod.GET.name(),
        HttpMethod.POST.name(),
        HttpMethod.PUT.name(),
        HttpMethod.DELETE.name(),
        HttpMethod.OPTIONS.name(),
      )

    private val ALLOWED_HEADERS =
      listOf(
        HttpHeaders.AUTHORIZATION,
        HttpHeaders.CONTENT_TYPE,
      )

    private val EXPOSED_HEADERS =
      listOf(
        HttpHeaders.AUTHORIZATION,
      )

    private val CORS_PREFLIGHT_MAX_AGE = Duration.ofSeconds(600).seconds
  }

  @Bean
  fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain =
    with(httpSecurity) {
      cors {
        it.configurationSource(corsConfigurationSource())
      }
      csrf {
        it.disable()
      }
      sessionManagement {
        it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      }
      authorizeHttpRequests {
        it.anyRequest().permitAll()
      }
      addFilterBefore(
        jwtAuthenticationFilter,
        UsernamePasswordAuthenticationFilter::class.java,
      )
      build()
    }

  @Bean
  fun corsConfigurationSource(): CorsConfigurationSource =
    UrlBasedCorsConfigurationSource().apply {
      registerCorsConfiguration(
        "/**",
        CorsConfiguration().apply {
          allowedOriginPatterns = ALLOWED_ORIGINS
          allowedMethods = ALLOWED_METHODS
          allowedHeaders = ALLOWED_HEADERS
          exposedHeaders = EXPOSED_HEADERS
          allowCredentials = true

          // CORS 환경 preflight 요청 캐싱 시간
          maxAge = CORS_PREFLIGHT_MAX_AGE
        },
      )
    }
}
