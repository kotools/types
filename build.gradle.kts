import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins { kotlin(module = "jvm") version "1.5.32" }

group = "org.kotools"
version = "0.0.0"

repositories(RepositoryHandler::mavenCentral)

kotlin { explicitApi = ExplicitApiMode.Strict }
