package org.kotools.types

import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.annotation.AnnotationTarget.TYPEALIAS

private const val OPT_IN_MESSAGE: String = "This declaration is experimental" +
        " and can be incompatibly changed in the future."

/**
 * Marks declarations that are still **experimental** in the API.
 *
 * @since 4.4.0
 */
@Deprecated(
    message = "Moved to 'kotools.types' package.",
    ReplaceWith("kotools.types.ExperimentalKotoolsTypesApi")
)
@MustBeDocumented
@RequiresOptIn(OPT_IN_MESSAGE)
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalKotoolsTypesApi
