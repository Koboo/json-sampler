import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version ("8.1.1")
    id("application")
}

group = "eu.koboo.javalin"
version = "1.0-SNAPSHOT"
var mainClassName = "eu.koboo.javalin.jsonsampler.Bootstrap";

application {
    mainClass.set(mainClassName)
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(rootProject.libs.lombok)
    annotationProcessor(rootProject.libs.lombok)

    // Webserver
    implementation("io.javalin:javalin:5.6.3")

    // Forward logging
    implementation("org.slf4j:slf4j-jdk14:2.0.9")

    // Json serialization/deserialization
    implementation("com.google.code.gson:gson:2.10.1")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    withSourcesJar()
}

tasks.withType<JavaCompile>().configureEach {
    options.isFork = true
    options.isIncremental = true
}

tasks.named<ShadowJar>("shadowJar") {
    manifest {
        attributes(mapOf(
                "Main-Class" to mainClassName,
                "Implementation-Version" to rootProject.version
        ))
    }
}