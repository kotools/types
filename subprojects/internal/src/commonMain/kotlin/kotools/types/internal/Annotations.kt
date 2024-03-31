package kotools.types.internal

import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION

/** Marks a declaration that shouldn't be used outside Kotools Types. */
@MustBeDocumented
@RequiresOptIn(
    "This declaration is internal and shouldn't be used outside Kotools Types."
)
@Retention(BINARY)
@Target(CLASS, FUNCTION)
public annotation class InternalKotoolsTypesApi
