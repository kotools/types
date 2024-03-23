import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    id("kotools.types.multiplatform")
    alias(libs.plugins.dokka)
    id("kotools.types.documentation")
}

repositories.mavenCentral()

dependencies {
    commonMainImplementation(platform(libs.kotlin.bom))
    commonTestImplementation(libs.kotlin.test)
}

tasks.jsBrowserTest.configure(KotlinJsTest::useMocha)

tasks.withType<DokkaTask>().configureEach {
    moduleName.set("Kotools Types 5")
    failOnWarning.set(true)
    dokkaSourceSets.configureEach {
        val moduleDocumentation: RegularFile =
            layout.projectDirectory.file("module.md")
        includes.setFrom(moduleDocumentation)
        reportUndocumented.set(true)
    }
}
