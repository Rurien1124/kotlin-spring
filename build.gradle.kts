plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.spring)
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.spring.dependency.management)
  alias(libs.plugins.ktlint)
}

group = "io.github.rurien"
version = "0.0.0.1"
description = "kotlin-spring"

java {
  toolchain {
    languageVersion =
      JavaLanguageVersion.of(
        libs.versions.java
          .get()
          .toInt(),
      )
  }
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(libs.bundles.spring.boot)
  implementation(libs.bundles.kotlin.core)
  implementation(libs.bundles.serialization)
  implementation(libs.bundles.logging)

  annotationProcessor(libs.spring.boot.config.processor)

  testImplementation(libs.bundles.spring.boot.test)
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll(
      "-Xjsr305=strict",
      "-Xannotation-default-target=param-property",
    )
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.bootJar {
  mainClass.set("io.github.rurien.MainApplication")
}
