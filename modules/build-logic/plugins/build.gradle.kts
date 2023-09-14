plugins { `kotlin-dsl` }

repositories.gradlePluginPortal()

dependencies { implementation(kotlin("gradle-plugin", embeddedKotlinVersion)) }
