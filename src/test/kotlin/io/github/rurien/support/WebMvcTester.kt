package io.github.rurien.support

import io.github.rurien.advice.GlobalRestExceptionHandler
import io.github.rurien.support.controller.ExceptionHandlerController
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@WebMvcTest(controllers = [ExceptionHandlerController::class])
@Import(GlobalRestExceptionHandler::class)
@ActiveProfiles("test")
annotation class WebMvcTester
