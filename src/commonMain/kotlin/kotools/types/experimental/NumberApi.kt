package kotools.types.experimental

import kotools.types.SinceKotoolsTypes
import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.*

/**
 * Declarations marked with this annotation are still **experimental** in the
 * number API.
 */
@MustBeDocumented
@RequiresOptIn
@Retention(BINARY)
@SinceKotoolsTypes("4.2")
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalNumberApi
