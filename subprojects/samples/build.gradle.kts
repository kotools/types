import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("kotools.types.base.v2")
    kotlin("jvm")
    id("kotools.types.samples")
}

repositories.mavenCentral()

group = "org.kotools"

kotlin.explicitApi()

dependencies {
    implementation(rootProject)
    implementation(platform(libs.kotlin.bom))

    testImplementation(libs.kotlin.test)
    testImplementation(libs.system.lambda)
}

tasks.withType<KotlinCompile>().configureEach {
    javaPackagePrefix = "kotools.types"
    compilerOptions {
        allWarningsAsErrors.set(true)
        jvmTarget.set(JvmTarget.JVM_17)
    }
}
tasks.apiCheck.configure { this.isEnabled = false }
tasks.apiDump.configure { this.isEnabled = false }
tasks.test.configure(Test::useJUnitPlatform)
