import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.kotools.types.base")
    kotlin("jvm")
}

kotlin.explicitApi()

dependencies {
    implementation(platform(libs.kotlin.bom))
    implementation(project(":types"))
    implementation(project(":types-kotlinx-serialization"))
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.system.lambda)
}

tasks.withType<KotlinCompile>().configureEach {
    javaPackagePrefix = "org.kotools.types"
    compilerOptions {
        allWarningsAsErrors.set(true)
        jvmTarget.set(JvmTarget.JVM_17)
    }
}
tasks.test.configure(Test::useJUnitPlatform)
