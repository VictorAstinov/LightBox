plugins {
    kotlin("jvm") version "1.8.20"
    application
    id("org.openjfx.javafxplugin") version "0.0.14"
}

group = "ui.lightbox"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}


application {
    mainClass.set("Main")
}

javafx {
    version = "18.0.2"
    modules("javafx.controls", "javafx.graphics")
}
