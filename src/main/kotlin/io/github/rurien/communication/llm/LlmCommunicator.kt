package io.github.rurien.communication.llm

interface LlmCommunicator {
  fun ask(
    chunks: List<String>,
    question: String,
  ): String

  fun List<String>.toContext() = joinToString("\n\n")
}
