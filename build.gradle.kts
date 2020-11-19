import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.20-RC"
    application
}

application {
    mainClassName = "com.charylin.game.AppKt"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.shaunxiao:kotlinGameEngine:v0.0.4")
}

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}