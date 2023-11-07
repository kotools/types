package kotools.types.experimental

import kotools.types.SinceKotoolsTypes
import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.annotation.AnnotationTarget.TYPEALIAS

/** Marks declarations that are still **experimental** in the API. */
@MustBeDocumented
@RequiresOptIn
@Retention(BINARY)
@SinceKotoolsTypes("4.3.2")
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalKotoolsTypesApi
