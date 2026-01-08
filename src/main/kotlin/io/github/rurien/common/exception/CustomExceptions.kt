package io.github.rurien.common.exception

class UnsupportedDocumentException : RuntimeException("지원하지 않는 파일 형식입니다")

class NonTextDocumentException : RuntimeException("텍스트 기반 문서만 지원 가능합니다")

class DocumentNotFoundException : RuntimeException("문서를 찾을 수 없습니다")

class GeminiResponseNullException : RuntimeException("Gemini 응답이 없습니다")

class OpenRouterResponseNullException : RuntimeException("OpenRouter 응답이 없습니다")
