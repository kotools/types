plugins { `kotlin-dsl` }

buildscript {
    dependencies { classpath("org.jetbrains.dokka:versioning-plugin:1.7.20") }
}

repositories.mavenCentral()

dependencies {
    val kotlinVersion = "1.7.21"
    implementation(kotlin("gradle-plugin", kotlinVersion))
    implementation(kotlin("serialization", kotlinVersion))
    val dokkaVersion = "1.7.20"
    implementation(
        "org.jetbrains.dokka:org.jetbrains.dokka.gradle.plugin:$dokkaVersion"
    )
    implementation("org.jetbrains.dokka:versioning-plugin:$dokkaVersion")
}
