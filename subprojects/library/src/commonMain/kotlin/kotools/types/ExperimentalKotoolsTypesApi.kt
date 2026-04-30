package kotools.types

import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.annotation.AnnotationTarget.TYPEALIAS

/**
 * Signals that the annotated declaration is experimental and can be
 * incompatibly changed in the future.
 *
 * @since 5.2.0
 */
@MustBeDocumented
@RequiresOptIn(
    "This declaration is experimental and can be incompatibly changed in the " +
            "future."
)
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalKotoolsTypesApi
