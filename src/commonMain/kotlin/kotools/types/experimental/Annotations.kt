package kotools.types.experimental

import kotools.types.SinceKotoolsTypes
import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.annotation.AnnotationTarget.TYPEALIAS

/** Marks declarations that are still **experimental** in the collection API. */
@MustBeDocumented
@RequiresOptIn
@Retention(BINARY)
@SinceKotoolsTypes("4.4")
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalCollectionApi

/** Marks declarations that are still **experimental** in the number API. */
@MustBeDocumented
@RequiresOptIn
@Retention(BINARY)
@SinceKotoolsTypes("4.2")
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalNumberApi

/** Marks declarations that are still **experimental** in the range API. */
@MustBeDocumented
@RequiresOptIn
@Retention(BINARY)
@SinceKotoolsTypes("4.2")
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalRangeApi

/** Marks declarations that are still **experimental** in the result API. */
@MustBeDocumented
@RequiresOptIn
@Retention(BINARY)
@SinceKotoolsTypes("4.2")
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalResultApi

/** Marks declarations that are still **experimental** in the text API. */
@MustBeDocumented
@RequiresOptIn
@Retention(BINARY)
@SinceKotoolsTypes("4.2")
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalTextApi
