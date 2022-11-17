import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl

plugins { kotlin("multiplatform") }

dependencies { commonMainImplementation(platform(kotlin("bom"))) }

kotlin {
    explicitApi = ExplicitApiMode.Strict
    jvm().compilations.all { kotlinOptions.jvmTarget = "1.8" }
    js(BOTH, KotlinJsTargetDsl::browser)
    linuxX64("linux")
    macosX64("macos")
    mingwX64("mingw")
}
