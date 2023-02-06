import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl

plugins {
    val kotlinVersion = "1.5.32"
    kotlin("multiplatform") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("org.jetbrains.dokka") version "1.5.31"
    `maven-publish`
    signing
}

group = "io.github.kotools"
version = "4.0.1"

repositories(RepositoryHandler::mavenCentral)

dependencies {
    // Kotlin
    commonMainImplementation(platform(kotlin("bom")))
    commonTestImplementation(kotlin("test"))

    // Serialization
    fun serialization(module: String): String =
        "org.jetbrains.kotlinx:kotlinx-serialization-$module:1.3.1"
    commonMainImplementation(serialization("core"))
    commonTestImplementation(serialization("json"))
}

kotlin {
    explicitApi = ExplicitApiMode.Strict
    js(IR, KotlinJsTargetDsl::browser)
    jvm {
        compilations.all { kotlinOptions.jvmTarget = "1.8" }
        testRuns["test"].executionTask { useJUnitPlatform() }
    }
    linuxX64("linux")
    macosX64("macos")
    mingwX64("windows")
}

tasks.withType<Jar> {
    fun key(suffix: String): String = "Implementation-$suffix"
    val name: Pair<String, String> = key("Title") to project.name
    val version: Pair<String, Any> = key("Version") to project.version
    manifest.attributes(name, version)
}
val dokkaDirectory: File = buildDir.resolve("dokka")
tasks.dokkaHtml {
    outputDirectory.set(dokkaDirectory)
    dokkaSourceSets {
        configureEach {
            includes.from += "packages.md"
            reportUndocumented.set(true)
            skipEmptyPackages.set(true)
        }
    }
}
val cleanDokkaHtml: TaskProvider<Delete> =
    tasks.register<Delete>("cleanDokkaHtml") { delete(dokkaDirectory) }
val javadocJar: TaskProvider<Jar> = tasks.register<Jar>("javadocJar") {
    dependsOn(cleanDokkaHtml, tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
    from(tasks.dokkaHtml)
}
tasks.assemble { dependsOn(javadocJar) }

publishing {
    repositories {
        maven {
            name = "OSSRH"
            url = uri(
                "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            )
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
    publications {
        getByName("kotlinMultiplatform", MavenPublication::class) {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
        }
        forEach {
            if (it !is MavenPublication) return@forEach
            it.artifact(javadocJar)
            it.pom {
                name.set("Kotools Types")
                description.set(
                    "Multiplatform library providing explicit types for Kotlin."
                )
                val gitRepository = "https://github.com/kotools/types"
                url.set(gitRepository)
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                issueManagement {
                    system.set("GitHub")
                    url.set("$gitRepository/issues")
                }
                scm {
                    connection.set("$gitRepository.git")
                    url.set(gitRepository)
                }
                developers {
                    developer {
                        name.set(System.getenv("GIT_USER"))
                        email.set(System.getenv("GIT_EMAIL"))
                    }
                }
            }
            signing {
                val secretKey: String? = System.getenv("GPG_PRIVATE_KEY")
                val password: String? = System.getenv("GPG_PASSWORD")
                useInMemoryPgpKeys(secretKey, password)
                sign(it)
            }
        }
    }
}
