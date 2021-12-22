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

repositories { mavenCentral() }

dependencies {
    implementation(platform(kotlin("bom")))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.2.0")

    testImplementation(kotlin("test"))
    testRuntimeOnly("org.slf4j:slf4j-simple:1.7.32")
}

kotlin.explicitApi = ExplicitApiMode.Strict

java {
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
}

tasks {
    compileJava { enabled = false }
    compileTestJava { enabled = false }

    jar {
        manifest.attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version
        )
    }

    val dokkaOutputDir = "$buildDir/dokka"
    dokkaHtml { outputDirectory.set(file(dokkaOutputDir)) }
    val cleanDokkaOutput =
        register<Delete>("cleanDokkaOutput") { delete(dokkaOutputDir) }
    val javadocJar = register<Jar>("javadocJar") {
        dependsOn(cleanDokkaOutput, dokkaHtml)
        archiveClassifier.set("javadoc")
        from(dokkaOutputDir)
    }
    assemble { dependsOn += javadocJar }

    test {
        testLogging.exceptionFormat = TestExceptionFormat.FULL
        useJUnitPlatform()
    }
}

publishing {
    publications {
        create<MavenPublication>(project.name) {
            from(components["java"])
            artifact(tasks.getByName("javadocJar"))
            pom {
                name.set("Kotools CSV")
                description.set("Elegant CSV file's manager for Kotlin.")
                val gitRepository = "https://github.com/kotools/csv"
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
            url = uri("https://maven.pkg.github.com/kotools/csv")
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
    useInMemoryPgpKeys(
        System.getenv("GPG_PRIVATE_KEY"),
        System.getenv("GPG_PASSWORD")
    )
    sign(publishing.publications)
}
