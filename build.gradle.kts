import org.jetbrains.dokka.versioning.VersioningConfiguration
import org.jetbrains.dokka.versioning.VersioningPlugin
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// ---------- Plugins ----------

buildscript { dependencies.classpath(libs.dokka.versioning) }

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dokka)
    `maven-publish`
    signing
}

// ---------- Project Details ----------

group = "org.kotools"
version = "4.3.1-SNAPSHOT"

// ---------- Extensions ----------

kotlin {
    explicitApi()
    js(IR) { browser() }
    jvm()
    jvmToolchain(17)
    linuxX64("linux")
    macosX64("macos")
    mingwX64("windows")
}

// ---------- Dependencies ----------

repositories.mavenCentral()

dependencies {
    dokkaHtmlPlugin(libs.dokka.versioning)

    commonMainImplementation(platform(libs.kotlin.bom))
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonTestImplementation(libs.kotlin.test)
    commonTestImplementation(libs.kotlinx.serialization.json)
}

// ---------- Tasks ----------

// Help tasks

tasks.register("version").configure {
    group = "help"
    description = "Displays this project's version."
    doLast { println(version) }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        languageVersion = "1.5"
    }
}

tasks.withType<KotlinJvmTest>().configureEach { useJUnitPlatform() }

tasks.withType<Jar> {
    fun key(suffix: String): String = "Implementation-$suffix"
    val name: Pair<String, String> = key("Title") to project.name
    val version: Pair<String, Any> = key("Version") to project.version
    manifest.attributes(name, version)
}

val dokkaDirectory: File = buildDir.resolve("dokka")
val projectName = "Kotools Types"
tasks.dokkaHtml {
    outputDirectory.set(dokkaDirectory)
    dokkaSourceSets {
        configureEach {
            includes.from += "packages.md"
            reportUndocumented.set(true)
            skipEmptyPackages.set(true)
        }
    }
    moduleName.set(projectName)
    val currentVersion = "${project.version}"
    val apiReferencesDir: File = rootDir.resolve("api/references")
    pluginConfiguration<VersioningPlugin, VersioningConfiguration> {
        version = currentVersion
        olderVersionsDir = apiReferencesDir
    }
    doLast {
        val images = "images"
        val logo = "logo-icon.svg"
        val dokkaImages: File = dokkaDirectory.resolve(images)
        val kotlinLogo: File = dokkaImages.resolve(logo)
        delete(kotlinLogo)
        copy {
            val kotoolsLogo: File = rootDir.resolve(images)
                .resolve(logo)
            from(kotoolsLogo)
            into(dokkaImages)
        }
        if ("SNAPSHOT" !in currentVersion) {
            copy {
                from(dokkaDirectory)
                into(apiReferencesDir.resolve(currentVersion))
            }
            delete(apiReferencesDir.resolve(currentVersion).resolve("older"))
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
        getByName<MavenPublication>("kotlinMultiplatform") {
            groupId = "${project.group}"
            artifactId = project.name
            version = "${project.version}"
        }
        forEach {
            if (it !is MavenPublication) return@forEach
            it.artifact(javadocJar)
            it.pom {
                name.set(projectName)
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
