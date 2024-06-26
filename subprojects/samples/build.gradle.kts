plugins {
    alias(libs.plugins.kotools.types.dev.tasks)
    alias(libs.plugins.kotools.types.kotlin.jvm)
}

devTasks.list(tasks.check)

kotlin.sourceSets.configureEach {
    languageSettings.optIn("kotools.types.internal.InternalKotoolsTypesApi")
}

dependencies {
    implementation(platform(libs.kotlin.bom))
    implementation(project(":types"))
    implementation(project(":types-internal"))
    implementation(project(":types-kotlinx-serialization"))
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.system.lambda)
}
