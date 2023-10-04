plugins {
    java
    id("io.qameta.allure") version "2.11.2"
}

tasks.withType(Wrapper::class) {
    gradleVersion = "8.1.1"
}


val allureVersion = "2.24.0"
val cucumberVersion = "7.13.0"
val aspectJVersion = "1.9.20"

tasks.withType(JavaCompile::class) {
    sourceCompatibility = "${JavaVersion.VERSION_17}"
    targetCompatibility = "${JavaVersion.VERSION_17}"
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

tasks.test {
    ignoreFailures = true
    useJUnitPlatform()
    systemProperty("profile", findProperty("profile"))
    jvmArgs = listOf(
            "-javaagent:${agent.singleFile}"
    )
}

dependencies {
    agent("org.aspectj:aspectjweaver:${aspectJVersion}")

    testImplementation(platform("io.cucumber:cucumber-bom:$cucumberVersion"))
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.11.1")
    testImplementation("io.cucumber:cucumber-java:7.11.1")

    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-cucumber7-jvm:2.21.0")
    testImplementation("io.qameta.allure:allure-junit-platform:2.21.0")
    testImplementation("io.qameta.allure:allure-selenide:2.24.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.platform:junit-platform-suite:1.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")

    testImplementation("org.slf4j:slf4j-simple:2.0.5")
    testImplementation("org.slf4j:slf4j-api:2.0.7")

    testImplementation("com.codeborne:selenide:6.18.0")
    compileOnly("org.projectlombok:lombok:1.18.28")
    testImplementation("org.projectlombok:lombok:1.18.28")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.28")
    testImplementation("org.yaml:snakeyaml:2.2")
    testImplementation("ch.qos.logback:logback-classic:1.4.11")
}

repositories {
    mavenCentral()
}