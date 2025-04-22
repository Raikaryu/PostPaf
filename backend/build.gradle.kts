plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.postpaf"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-rest:3.4.4")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web:3.4.4")
    runtimeOnly("org.postgresql:postgresql:42.7.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.7.1")
    implementation("me.paulschwarz:spring-dotenv:4.0.0")
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.7.1")
    testImplementation ("org.mockito:mockito-junit-jupiter:3.11.2")
    testImplementation ("org.mockito:mockito-core:3.11.2")
}
tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    jvmArgs = listOf("--add-opens=java.base/java.io=ALL-UNNAMED")
}


