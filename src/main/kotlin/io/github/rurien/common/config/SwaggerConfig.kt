package io.github.rurien.common.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

const val SECURITY_SCHEME = "bearerAuth"

@Configuration
class SwaggerConfig {
  @Bean
  fun openAPI(): OpenAPI {
    val bearerAuth =
      SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")

    return OpenAPI().apply {
      info =
        Info()
          .title("My API document title")
          .description("My API document description")
          .version("v1")

      components =
        Components().addSecuritySchemes(
          SECURITY_SCHEME,
          bearerAuth,
        )

      addSecurityItem(
        SecurityRequirement().addList(SECURITY_SCHEME),
      )
    }
  }
}
