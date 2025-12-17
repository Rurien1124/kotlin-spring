package io.github.rurien.common.exception

class UnsupportedDocumentException : RuntimeException("지원하지 않는 파일 형식입니다")

class NonTextDocumentException : RuntimeException("텍스트 기반 문서만 지원 가능합니다")
