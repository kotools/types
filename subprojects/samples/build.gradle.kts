plugins {
    id("org.kotools.types.gradle.tasks")
    id("org.kotools.types.kotlin.jvm")
}

devTasks.list(tasks.check)

kotlin.sourceSets.test {
    languageSettings.optIn("kotools.types.internal.InternalKotoolsTypesApi")
}

dependencies {
    implementation(platform(libs.kotlin.bom))
    implementation(project(":types"))
    implementation(project(":types-kotlinx-serialization"))
    implementation(libs.kotlinx.serialization.json)

    testImplementation(project(":types-internal"))
    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.system.lambda)
}
