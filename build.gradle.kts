import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
    kotlin("jvm") version embeddedKotlinVersion
    `java-library`
    id("org.jetbrains.dokka") version embeddedKotlinVersion
    `maven-publish`
    signing
}

group = "io.github.kotools"
version = "2.0.0-SNAPSHOT"

repositories(RepositoryHandler::mavenCentral)

dependencies {
    // Kotlin
    implementation(platform(kotlin("bom")))
    testImplementation(kotlin("test"))

    // Kotools
    testImplementation("io.github.kotools", "assert", "[2.0,2.1[")
}

kotlin.explicitApi = ExplicitApiMode.Strict

java {
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
}

// ---------- Tasks ----------

tasks.compileJava { enabled = false }
tasks.compileTestJava { enabled = false }

tasks.jar {
    fun key(suffix: String): String = "Implementation-$suffix"
    val name: Pair<String, String> = key("Title") to project.name
    val version: Pair<String, Any> = key("Version") to project.version
    manifest.attributes(name, version)
}

val dokkaDirectory: File = buildDir.resolve("dokka")
tasks.dokkaHtml { outputDirectory.set(dokkaDirectory) }
val cleanDokkaHtml: TaskProvider<Delete> =
    tasks.register<Delete>("cleanDokkaHtml") { delete(dokkaDirectory) }

val javadocJar: TaskProvider<Jar> = tasks.register<Jar>("javadocJar") {
    dependsOn(cleanDokkaHtml, tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaDirectory)
}

tasks.assemble { dependsOn(javadocJar) }

tasks.test {
    testLogging.exceptionFormat = TestExceptionFormat.FULL
    useJUnitPlatform()
}

// ---------- Publishing & signing ----------

publishing {
    publications {
        create<MavenPublication>(project.name) {
            from(components["java"])
            artifact(javadocJar)
            pom {
                name.set("Kotools Types")
                description.set("Commonly used types for Kotlin.")
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
        }
    }
    repositories {
        maven {
            name = "GitHub"
            url = uri("https://maven.pkg.github.com/kotools/types")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
        maven {
            name = "OSSRH"
            val uri: String = if (version.toString().endsWith("SNAPSHOT"))
                "content/repositories/snapshots/"
            else "service/local/staging/deploy/maven2/"
            url = uri("https://s01.oss.sonatype.org/$uri")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
}

signing {
    val secretKey: String? = System.getenv("GPG_PRIVATE_KEY")
    val password: String? = System.getenv("GPG_PASSWORD")
    useInMemoryPgpKeys(secretKey, password)
    sign(publishing.publications)
}
