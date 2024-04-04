plugins { alias(libs.plugins.kotlin.multiplatform) apply false }

allprojects {
    group = "org.kotools"
    repositories.mavenCentral()
}
