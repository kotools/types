import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins { kotlin("jvm") }

group = "org.kotools"

repositories.mavenCentral()

kotlin.explicitApi()

dependencies {
    implementation(projects.types5)
    implementation(libs.kotlin.bom)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.system.lambda)
}

tasks.withType<KotlinCompile>().configureEach {
    javaPackagePrefix = "${project.group}.types"
    compilerOptions {
        allWarningsAsErrors.set(true)
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

tasks.test.configure(Test::useJUnitPlatform)
