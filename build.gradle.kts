plugins {
  kotlin("jvm") version "2.2.21"
  kotlin("plugin.spring") version "2.2.21"
  id("org.springframework.boot") version "4.0.0"
  id("io.spring.dependency-management") version "1.1.7"
  id("org.jlleitschuh.gradle.ktlint") version "14.0.1"
}

group = "io.github.rurien"
version = "0.0.0.1"
description = "kotlin-spring"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
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
  implementation(libs.bundles.spring.boot.web)
  implementation(libs.bundles.springdoc)
  implementation(libs.bundles.kotlin.core)
  implementation(libs.bundles.serialization)
  implementation(libs.bundles.document)
  implementation(libs.bundles.logging)
  implementation(libs.bundles.jwt)
  implementation(libs.bundles.llm)

  runtimeOnly(libs.jjwt.impl)
  runtimeOnly(libs.jjwt.jackson)

  developmentOnly(libs.spring.boot.devtools)

  annotationProcessor(libs.spring.boot.config.processor)

  testImplementation(libs.bundles.spring.boot.test)
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.bootJar {
  mainClass.set("io.github.rurien.MainApplication")
}
