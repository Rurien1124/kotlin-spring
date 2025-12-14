package io.github.rurien.advice

import io.github.rurien.support.controller.ExceptionHandlerController
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [ExceptionHandlerController::class])
@Import(GlobalRestExceptionHandler::class)
class GlobalRestExceptionHandlerTest {
  @Autowired
  lateinit var mockMvc: MockMvc

  @Test
  @DisplayName("Does unexpected exception handled")
  fun handleUnexpectedException() {
    mockMvc
      .perform(get("/unexpected-exception"))
      .andExpect(status().isInternalServerError)
      .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
  }
}
