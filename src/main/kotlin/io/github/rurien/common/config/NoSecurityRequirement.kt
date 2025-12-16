package io.github.rurien.common.config

import io.swagger.v3.oas.annotations.security.SecurityRequirements

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@SecurityRequirements
annotation class NoSecurityRequirement
