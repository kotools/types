plugins { `kotlin-dsl` }

buildscript {
    dependencies { classpath(libs.dokka.versioning) }
}

repositories.mavenCentral()

dependencies {
    implementation(libs.kotlin.gradle)
    implementation(libs.kotlin.serialization)
    implementation(libs.dokka.gradle)
    implementation(libs.dokka.versioning)
}
