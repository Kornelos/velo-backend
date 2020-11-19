import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.0"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("com.palantir.git-version") version "0.12.3"
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.spring") version "1.4.10"
    kotlin("plugin.serialization") version "1.4.10"

}
val gitVersion: groovy.lang.Closure<String> by extra
group = "pl.edu.pw.mini"
version = gitVersion()
java.sourceCompatibility = JavaVersion.VERSION_14

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")
    implementation("com.sendgrid:sendgrid-java:4.0.1")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springdoc:springdoc-openapi-ui:1.4.8")
    implementation("io.sentry:sentry-spring-boot-starter:3.1.1")
    implementation("io.sentry:sentry-logback:3.1.1")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.amshove.kluent:kluent:1.61")
    testImplementation("com.github.tomakehurst:wiremock:2.27.2")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner:2.2.5.RELEASE")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:2.2.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "14"
    }
}
