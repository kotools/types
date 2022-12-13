import kotools.gradle.Git
import kotools.gradle.GitHub
import kotools.gradle.Gpg
import kotools.gradle.Maven
import org.jetbrains.dokka.Platform
import org.jetbrains.dokka.gradle.GradleDokkaSourceSetBuilder
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jetbrains.dokka")
    `maven-publish`
    signing
}

version = "3.2.0"

lateinit var commonSourceSet: KotlinSourceSet
lateinit var jvmSourceSet: KotlinSourceSet
lateinit var jsSourceSet: KotlinSourceSet
lateinit var nativeSourceSet: KotlinSourceSet
lateinit var linuxSourceSet: KotlinSourceSet
lateinit var macosSourceSet: KotlinSourceSet
lateinit var windowsSourceSet: KotlinSourceSet

java.targetCompatibility = JavaVersion.VERSION_1_8

kotlin {
    explicitApi = ExplicitApiMode.Strict
    jvm {
        compilations.all { kotlinOptions.jvmTarget = "1.8" }
        testRuns["test"].executionTask.configure(Test::useJUnitPlatform)
    }
    js(IR, KotlinJsTargetDsl::browser)
    linuxX64()
    macosX64()
    mingwX64()
    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val commonMain: KotlinSourceSet by getting {
            dependencies {
                implementation(project.dependencies.platform(kotlin("bom")))
                implementation(kotlinx.serialization.json)
                implementation(project(":shared"))
            }
        }
        val commonTest: KotlinSourceSet by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotools.assert)
            }
        }
        commonSourceSet = commonMain
        val jsMain: KotlinSourceSet by getting
        jsSourceSet = jsMain
        val jvmMain: KotlinSourceSet by getting
        val jvmTest: KotlinSourceSet by getting
        jvmSourceSet = jvmMain
        val nativeMain: KotlinSourceSet by creating { dependsOn(commonMain) }
        val nativeTest: KotlinSourceSet by creating { dependsOn(commonTest) }
        nativeSourceSet = nativeMain
        val linuxX64Main: KotlinSourceSet by getting { dependsOn(nativeMain) }
        val linuxX64Test: KotlinSourceSet by getting { dependsOn(nativeTest) }
        linuxSourceSet = linuxX64Main
        val macosX64Main: KotlinSourceSet by getting { dependsOn(nativeMain) }
        val macosX64Test: KotlinSourceSet by getting { dependsOn(nativeTest) }
        macosSourceSet = macosX64Main
        val mingwX64Main: KotlinSourceSet by getting { dependsOn(nativeMain) }
        val mingwX64Test: KotlinSourceSet by getting { dependsOn(nativeTest) }
        windowsSourceSet = mingwX64Main
    }
}

// ---------- Tasks ----------

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
        fun GradleDokkaSourceSetBuilder.sourceRoots(
            vararg sourceSets: KotlinSourceSet
        ): Unit = sourceSets.forEach { sourceRoots.from(it.kotlin.srcDirs) }

        configureEach {
            includes.from += "packages.md"
            reportUndocumented.set(true)
            skipEmptyPackages.set(true)
        }
        named(commonSourceSet.name) { displayName.set("All platforms") }
        named(jsSourceSet.name) {
            displayName.set("JS")
            platform.set(Platform.js)
            dependsOn(commonSourceSet.name)
            sourceRoots(jsSourceSet)
        }
        named(jvmSourceSet.name) {
            displayName.set("JVM")
            platform.set(Platform.jvm)
            dependsOn(commonSourceSet.name)
            sourceRoots(jvmSourceSet)
        }
        named(nativeSourceSet.name) {
            displayName.set("Native")
            platform.set(Platform.native)
            dependsOn(commonSourceSet.name)
            sourceRoots(
                nativeSourceSet,
                linuxSourceSet,
                macosSourceSet,
                windowsSourceSet
            )
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

// ---------- Publishing & signing ----------

val isSnapshot: Boolean by lazy { version.toString().endsWith("SNAPSHOT") }

tasks {
    withType<Sign> {
        val taskNames: List<String> = project.gradle.startParameter.taskNames
        val isPublishingToMavenLocal: Boolean =
            publishToMavenLocal.name in taskNames
        onlyIf { !isSnapshot && !isPublishingToMavenLocal }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHub"
            url = uri("https://maven.pkg.github.com/kotools/types")
            credentials {
                username = System.getenv(GitHub.username)
                password = System.getenv(GitHub.password)
            }
        }
        maven {
            name = "OSSRH"
            val uriSuffix: String =
                if (isSnapshot) "content/repositories/snapshots/"
                else "service/local/staging/deploy/maven2/"
            url = uri("https://s01.oss.sonatype.org/$uriSuffix")
            credentials {
                username = System.getenv(Maven.username)
                password = System.getenv(Maven.password)
            }
        }
    }
    publications {
        @Suppress("UNUSED_VARIABLE")
        val kotlinMultiplatform by getting(MavenPublication::class) {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
        }
        forEach {
            if (it !is MavenPublication) return@forEach
            it.artifact(javadocJar)
            it.pom {
                name.set("Kotools Types")
                description.set("Commonly used types for Kotlin.")
                val gitRepository = "https://github.com/kotools/libraries"
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
                        name.set(System.getenv(Git.user))
                        email.set(System.getenv(Git.email))
                    }
                }
            }
            signing {
                val secretKey: String? = System.getenv(Gpg.secretKey)
                val password: String? = System.getenv(Gpg.password)
                useInMemoryPgpKeys(secretKey, password)
                sign(it)
            }
        }
    }
}
