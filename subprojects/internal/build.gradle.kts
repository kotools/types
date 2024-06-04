plugins {
    alias(libs.plugins.kotools.types.dev.tasks)
    alias(libs.plugins.kotools.types.kotlin.multiplatform)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotools.types.documentation)
    `maven-publish`
    signing
    alias(libs.plugins.kotools.types.publication)
}

devTasks.list(tasks.checkJs, tasks.checkJvm)

documentation.excludeFromParentApiReference = true

dependencies {
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonTestImplementation(libs.kotlin.test.common)
    commonTestImplementation(libs.kotlin.test.common.annotations)

    jvmTestImplementation(libs.kotlin.test.junit5)

    jsTestImplementation(libs.kotlin.test.js)
}
