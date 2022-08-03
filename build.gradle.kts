import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
    kotlin("jvm") version embeddedKotlinVersion
    `java-library`
    id("org.jetbrains.dokka") version embeddedKotlinVersion
    `maven-publish`
    signing
}

group = "io.github.kotools"
version = "2.1.0"

repositories(RepositoryHandler::mavenCentral)

dependencies {
    // Kotlin
    implementation(platform(kotlin("bom")))
    implementation(kotlin("test"))

    // JUnit
    implementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
}

kotlin.explicitApi = ExplicitApiMode.Strict

java {
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
}

// ---------- Tasks ----------

tasks.compileJava { enabled = false }
tasks.processResources { enabled = false }
tasks.compileTestKotlin { enabled = false }
tasks.compileTestJava { enabled = false }
tasks.processTestResources { enabled = false }
tasks.testClasses { enabled = false }
tasks.test { enabled = false }

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

// ---------- Publishing & signing ----------

publishing {
    publications {
        create<MavenPublication>(project.name) {
            from(components["java"])
            artifact(javadocJar)
            pom {
                name.set("Kotools Assert")
                description.set("Lightweight and elegant assertions library.")
                val gitRepository = "https://github.com/kotools/assert"
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
                        val gitUser: String? = System.getenv("GIT_USER")
                        name.set(gitUser)
                        val gitEmail: String? = System.getenv("GIT_EMAIL")
                        email.set(gitEmail)
                    }
                }
            }
        }
    }
    repositories {
        maven {
            name = "GitHub"
            url = uri("https://maven.pkg.github.com/kotools/assert")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
        maven {
            name = "OSSRH"
            val isSnapshot: Boolean = version.toString().endsWith("SNAPSHOT")
            val uri: String = if (isSnapshot) "content/repositories/snapshots/"
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
