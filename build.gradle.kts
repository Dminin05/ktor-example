import io.ktor.plugin.features.*

val kotlin_version: String by project
val logback_version: String by project
val postgres_version: String by project
val h2_version: String by project
val exposed_version: String by project

plugins {
    kotlin("jvm") version "2.0.21"
    id("io.ktor.plugin") version "3.0.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    // KTOR
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-status-pages-jvm")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-jackson-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")

    // DI
    implementation("org.kodein.di:kodein-di-jvm:7.22.0")

    // DB
    implementation("org.postgresql:postgresql:$postgres_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("com.amazonaws:aws-java-sdk:1.12.651")

    // CONFIG
    implementation("io.github.config4k:config4k:0.7.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.3")
    implementation("javax.xml.bind:jaxb-api:2.3.1")

    // SECURITY
    implementation("org.mindrot:jbcrypt:0.4")

    // TESTS
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

ktor {
    docker {
        val username = System.getenv("DOCKER_USERNAME")
        val password = System.getenv("DOCKER_PASSWORD")
        localImageName.set("$username/nutrify")
        imageTag.set("latest")
        externalRegistry.set(
            DockerImageRegistry.dockerHub(
                appName = provider { "nutrify" },
                username = provider { username },
                password = provider { password }
            )
        )
    }
}

tasks.register("buildAndPushDocker") {
    group = "docker"
    description = "Собирает проект, создает Docker-образ и отправляет его в реестр"

    dependsOn("clean", "buildImage", "publishImage")
}
