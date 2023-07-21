import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.10"
    id("me.champeau.gradle.jmh") version "0.5.3"
    id("org.jetbrains.dokka") version "1.8.10"
    id("org.jlleitschuh.gradle.ktlint") version "11.4.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")
    testImplementation(kotlin("test"))
    testImplementation("org.assertj:assertj-core:3.24.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

jmh {
    threads = 1
    fork = 1
    warmupIterations = 1
    iterations = 1
}