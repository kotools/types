import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    id("kotools.types.multiplatform")
}

repositories.mavenCentral()

dependencies {
    commonMainImplementation(platform(libs.kotlin.bom))
    commonTestImplementation(libs.kotlin.test)
}

tasks.jsBrowserTest.configure(KotlinJsTest::useMocha)
