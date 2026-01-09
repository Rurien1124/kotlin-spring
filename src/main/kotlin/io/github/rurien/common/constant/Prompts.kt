package io.github.rurien.common.constant

object Prompts {
  object Gemini {
    const val SUMMARY_PROMPT = """
      제시된 [문서]를 다음 규칙에 따라 요약하세요.

      1. 전체 내용을 3~5개의 논리적 섹션으로 나누어 요약할 것.
      2. 문장 형태가 아닌 '- ~함', '- ~임'과 같은 **명사형 불렛 포인트**로 작성할 것 (토큰 절약).
      3. 문서의 마지막 줄까지 빠짐없이 반영할 것.
      4. 요약이 끝나면 마지막에 반드시 "--- 요약 완료 ---"를 붙일 것.
    """

    const val ASK_PROMPT = """
      제시된 [문서]를 기반으로 다음 규칙에 따라 답변하세요.

      1. 문서의 핵심을 파악하여 질문에 구체적인 수치나 키워드를 포함해 답변하세요.
      2. 만약 사용자가 '질문을 추천해달라'거나 모호하게 질문하면, 문서 내에서 가장 논쟁적이거나 중요한 포인트 3가지를 뽑아 질문 형태로 제시하세요.
      3. 답변은 최소 3문장 이상의 완성된 문단으로 작성하세요.
      4. 인사말이나 불필요한 서술은 제외하고 바로 답변을 시작하세요.
    """
  }

  object Nemotron {
    const val SUMMARY_PROMPT = """
      [Task] Summarize the provided [Document] in Korean.
      [Rules]
      1. Divide the content into 3-5 logical sections with headers.
      2. Use only noun-ending.
      3. Ensure the summary covers the entire text until the last line.
      4. End the response with "--- 요약 완료 ---".

      [문서]를 위 규칙에 따라 한국어로 요약하세요.
    """

    const val ASK_PROMPT = """
      [Task] Answer the question based on the [Document] in Korean.
      [Rules]
      1. Provide specific numbers and keywords from the text.
      2. If the user asks for "recommended questions" or is vague, suggest 3 critical/controversial points as questions.
      3. Use at least 3 complete sentences for the paragraph.
      4. DO NOT include greetings. Start the answer immediately.

      [문서]를 기반으로 질문에 한국어로 답변하세요.
    """
  }
}
