package org.kotools.types.internal

/** Marks a declaration that shouldn't be used outside Kotools Types. */
@MustBeDocumented
@RequiresOptIn(
    "This declaration is internal and shouldn't be used outside Kotools Types."
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
public annotation class InternalKotoolsTypesApi
