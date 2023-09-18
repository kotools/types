plugins { `kotlin-dsl` }

repositories.mavenCentral()

dependencies {
    val kotlinVersion = "1.7.21"
    implementation(kotlin("gradle-plugin", kotlinVersion))
    implementation(kotlin("serialization", kotlinVersion))
}
