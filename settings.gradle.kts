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
        fun kotlinx(module: String, version: String): String {
            val prefix = "kotlinx"
            return "org.jetbrains.$prefix:$prefix-$module:$version"
        }
        library("coroutines.core", kotlinx("coroutines-core", "[1.5,1.6["))
        library("serialization.json", kotlinx("serialization-json", "1.3.1"))
    }
    create("kotools") {
        fun kotools(module: String, version: String): String =
            "io.github.kotools:$module:$version"
        library("assert", kotools("assert", "[3.0,3.1["))
        library("csv", kotools("csv", "[2.2,2.3["))
        library("types", kotools("types", "[3.1,3.2["))
    }
    create("slf4j") { library("simple", "org.slf4j:slf4j-simple:[2.0,2.1[") }
}
