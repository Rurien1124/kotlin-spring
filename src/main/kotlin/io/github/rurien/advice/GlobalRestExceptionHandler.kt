package io.github.rurien.advice

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

/** 전역 RestController 예외 처리 */
@RestControllerAdvice
class GlobalRestExceptionHandler {
  private val log = KotlinLogging.logger {}

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Throwable::class)
  fun handleUnexpectedException(throwable: Throwable): ProblemDetail {
    val message = "Unexpected exception occurred"

    log.error(throwable) { message }

    return createErrorDetail(
      throwable = throwable,
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
      message = message,
    )
  }

  /** 공통 예외 처리 응답 */
  private fun createErrorDetail(
    throwable: Throwable,
    httpStatus: HttpStatus,
    message: String,
  ): ProblemDetail {
    val problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, message)

    problemDetail.setProperty("timestamp", LocalDateTime.now())
    problemDetail.setProperty("exception", throwable.javaClass.simpleName)

    return problemDetail
  }
}
