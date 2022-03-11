import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.6.10"
    id("org.springframework.boot") version "2.6.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("idea")
    id("jacoco")
    id("net.linguica.maven-settings") version "0.5"
    id("org.owasp.dependencycheck") version "7.0.0"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
}

group = "com.springboot.jooq.flyway"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

object V {
    const val JOOQ ="3.14.15"
    const val FLYWAY = "8.5.1"
    const val TEST_CONTAINERS = "1.16.3"
    const val KOTLIN_LOGGING = "2.1.21"
    const val LOGBACK_ENCODER ="7.0.1"
    const val AWAITILITY_KOTLIN="4.2.0"
    const val MOCKITO_KOTLIN = "2.2.0"
}



dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jooq"){
        exclude("org.jooq", "jooq")
    }

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.github.microutils:kotlin-logging:${V.KOTLIN_LOGGING}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("net.logstash.logback:logstash-logback-encoder:${V.LOGBACK_ENCODER}")

    //DB dependency
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core:${V.FLYWAY}")
    implementation("org.jooq:jooq:${V.JOOQ}")
    implementation("org.jooq:jooq-codegen-maven:${V.JOOQ}")
    implementation("org.jooq:jooq-meta:${V.JOOQ}")

    //TEST dependency
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql:${V.TEST_CONTAINERS}")
    testImplementation("org.awaitility:awaitility-kotlin:${V.AWAITILITY_KOTLIN}")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:${V.MOCKITO_KOTLIN}")
}


sourceSets {
    main {
        java {
            srcDirs("src/main/jooq", "src/main/kotlin")
        }
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    withType<Test> {
        useJUnitPlatform()
        finalizedBy(jacocoTestReport)
    }

    task("jooqCodeGen", JavaExec::class) {
        mainClass.set("jooqutils.generator.JooqCodeGenKt")
        classpath = sourceSets["test"].runtimeClasspath
    }

    getByName<Jar>("jar") {
        enabled = false
    }

    jacocoTestReport {
        dependsOn(test)
        finalizedBy(jacocoTestCoverageVerification)
        reports {
            html.required.set(true)
            xml.required.set(false)
            csv.required.set(false)
        }
    }

    jacocoTestCoverageVerification {
        violationRules {
            rule {
                limit {
                    minimum = "0.0".toBigDecimal()
                }
            }
        }
    }

    check {
        dependsOn(jacocoTestCoverageVerification)
    }
}