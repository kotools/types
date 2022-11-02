rootProject.name = "kotools"
include("assert", "csv", "types")

@Suppress("UnstableApiUsage")
dependencyResolutionManagement.versionCatalogs {
    create("doyaaaaaken") {
        library("kotlin.csv", "com.github.doyaaaaaken:kotlin-csv:[1.2,1.3[")
    }
    create("junit") {
        library("jupiter.api", "org.junit.jupiter:junit-jupiter-api:[5.6,5.7[")
    }
    create("kotlinx") {
        fun kotlinx(artifact: String, version: String): String {
            val prefix = "kotlinx"
            return "org.jetbrains.$prefix:$prefix-$artifact:$version"
        }
        library("coroutines.core", kotlinx("coroutines-core", "[1.5,1.6["))
        library("serialization.json", kotlinx("serialization-json", "1.3.1"))
    }
    create("slf4j") { library("simple", "org.slf4j:slf4j-simple:[1.7,1.8[") }
}
