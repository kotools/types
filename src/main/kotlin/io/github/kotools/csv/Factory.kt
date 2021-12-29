package io.github.kotools.csv

import kotlin.reflect.full.primaryConstructor

internal object Factory {
    @Throws(CsvException::class)
    inline infix fun <reified T : Configurable> create(
        configuration: T.() -> Unit
    ): T = createOrNull(configuration) ?: invalidConfigurationException()

    inline infix fun <reified T : Configurable> createOrNull(
        configuration: T.() -> Unit
    ): T? = T::class.primaryConstructor?.call()
        ?.apply(configuration)
        ?.takeIf(Configurable::isValid)
}
