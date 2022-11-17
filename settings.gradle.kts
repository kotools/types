rootProject.name = "kotools"
include("assert", "csv", "shared", "types")

@Suppress("UnstableApiUsage")
dependencyResolutionManagement.versionCatalogs {
    create("doyaaaaaken") {
        library("kotlin.csv", "com.github.doyaaaaaken:kotlin-csv:1.2.0")
    }
    create("junit") {
        library("jupiter.api", "org.junit.jupiter:junit-jupiter-api:5.6.3")
    }
    create("kotlinx") {
        fun kotlinx(module: String, version: String): String {
            val prefix = "kotlinx"
            return "org.jetbrains.$prefix:$prefix-$module:$version"
        }
        library("coroutines.core", kotlinx("coroutines-core", "1.5.2"))
        library("serialization.json", kotlinx("serialization-json", "1.3.1"))
    }
    create("kotools") {
        fun kotools(module: String, version: String): String =
            "io.github.kotools:$module:$version"
        library("assert", kotools("assert", "3.0.2"))
        library("csv", kotools("csv", "2.2.1"))
        library("types", kotools("types", "3.1.0"))
        library("types.next", kotools("types", "3.2.0-alpha.1"))
    }
    create("slf4j") { library("simple", "org.slf4j:slf4j-simple:2.0.3") }
}
