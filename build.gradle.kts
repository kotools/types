import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
    kotlin(module = "jvm") version Kotlin.version
    id("org.jetbrains.dokka") version "1.5.31"
    `maven-publish`
    signing
}

group = "io.github.kotools"
version = "0.1.2"

repositories(RepositoryHandler::mavenCentral)

dependencies {
    setOf(Kotlin.coroutines, Library.csv)
        .forEach(::implementation)
    testImplementation(kotlin(module = "test"))
    testRuntimeOnly(Library.slf4j)
}

kotlin.explicitApi = ExplicitApiMode.Strict

val dokkaOutputDir = "$buildDir/dokka"
tasks.dokkaHtml { outputDirectory.set(file(dokkaOutputDir)) }
val cleanDokkaOutput: TaskProvider<Delete> =
    tasks.register(name = "cleanDokkaOutput") { delete(dokkaOutputDir) }
val javadocJar: TaskProvider<Jar> = tasks.register(name = "javadocJar") {
    dependsOn(cleanDokkaOutput, tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaOutputDir)
}

publishing {
    publications {
        register<MavenPublication>(name = "gpr") { from(components["java"]) }
        withType<MavenPublication> {
            artifact(javadocJar)
            pom {
                name.set("Kotools CSV")
                description.set("Elegant CSV file's manager for Kotlin.")
                url.set("https://github.com/kotools/csv")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/kotools/csv/issues")
                }
                scm {
                    connection.set("https://github.com/kotools/csv.git")
                    url.set("https://github.com/kotools/csv")
                }
                developers {
                    developer {
                        name.set("Loïc Lamarque")
                        email.set("loiclamarque777@gmail.com")
                    }
                }
            }
        }
    }
    repositories {
        maven {
            name = "GitHub"
            url = uri(path = "https://maven.pkg.github.com/kotools/csv")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
        maven {
            name = "OSSRH"
            val path: String =
                if (version.toString().endsWith(suffix = "SNAPSHOT"))
                    "https://oss.sonatype.org/content/repositories/snapshots/"
                else "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            url = uri(path)
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(
        System.getenv("GPG_PRIVATE_KEY"),
        System.getenv("GPG_PASSWORD")
    )
    sign(publishing.publications)
}
