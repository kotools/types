import org.jetbrains.dokka.gradle.DokkaTask
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
    commonMainImplementation(platform(libs.kotlin.bom))
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonTestImplementation(libs.kotlin.test)
    commonTestImplementation(libs.kotlinx.serialization.json)

    dokkaHtmlPlugin(libs.dokka.versioning)
}

// ---------- Tasks ----------

enum class TaskGroup {
    DOCUMENTATION, HELP;

    override fun toString(): String = name.toLowerCase()
}

fun Task.group(value: TaskGroup) {
    group = "$value"
}

fun Task.description(value: String) {
    require(value.isNotBlank()) {
        "The task '$path' shouldn't have a blank description."
    }
    description = value
}

tasks.register<DependencyReportTask>("runtimeDependencies").configure {
    group(TaskGroup.HELP)
    description("Displays the runtime dependencies for all source sets.")
    setConfiguration("allSourceSetsRuntimeDependenciesMetadata")
}

tasks.register("version").configure {
    group(TaskGroup.HELP)
    description("Displays this project's version.")
    doLast { println(version) }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        languageVersion = "1.5"
    }
}

tasks.withType<KotlinJvmTest>().configureEach { useJUnitPlatform() }

tasks.withType<Jar>().configureEach {
    fun key(suffix: String): String = "Implementation-$suffix"
    val name: Pair<String, String> = key("Title") to project.name
    val version: Pair<String, Any> = key("Version") to project.version
    manifest.attributes(name, version)
}

val projectName = "Kotools Types"
val apiReferencesDir: Directory = layout.projectDirectory.dir("api/references")
val setApiReferenceLogoTask: TaskProvider<Copy> =
    tasks.register<Copy>("setApiReferenceLogo")
val archiveApiReferenceTask: TaskProvider<Copy> =
    tasks.register<Copy>("archiveApiReference")

tasks.dokkaHtml.configure {
    moduleName.set(projectName)
    dokkaSourceSets.configureEach {
        includes.from += layout.projectDirectory.file("packages.md")
        reportUndocumented.set(true)
        skipEmptyPackages.set(true)
    }
    val currentVersion = "${project.version}"
    pluginConfiguration<VersioningPlugin, VersioningConfiguration> {
        version = currentVersion
        olderVersionsDir = apiReferencesDir.asFile
    }
    val outputDir: Provider<File> = layout.buildDirectory.dir("dokka")
        .map { it.asFile }
    outputDirectory.set(outputDir)
    finalizedBy(setApiReferenceLogoTask, archiveApiReferenceTask)
}

val cleanDokkaHtmlTask: TaskProvider<Delete> =
    tasks.register<Delete>("cleanDokkaHtml") {
        val task: DokkaTask = tasks.dokkaHtml.get()
        this.setDelete(task.outputDirectory)
    }
tasks.clean.configure { dependsOn += cleanDokkaHtmlTask }

setApiReferenceLogoTask.configure {
    group(TaskGroup.DOCUMENTATION)
    description("Sets the Kotools logo into the API reference.")
    val images = "images"
    val source: RegularFile =
        layout.projectDirectory.file("$images/logo-icon.svg")
    from(source)
    val destination: Provider<File> = tasks.dokkaHtml.get()
        .outputDirectory
        .map { it.resolve(images) }
    into(destination)
}

archiveApiReferenceTask.configure {
    group(TaskGroup.DOCUMENTATION)
    description("Archives the API reference.")
    onlyIf { "SNAPSHOT" !in "${project.version}" }
    from(tasks.dokkaHtml)
    val destination: Directory = apiReferencesDir.dir("${project.version}")
    into(destination)
    doLast { delete(apiReferencesDir.dir("${project.version}/older")) }
}

val javadocJar: TaskProvider<Jar> = tasks.register<Jar>("javadocJar") {
    dependsOn(tasks.dokkaHtml)
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
