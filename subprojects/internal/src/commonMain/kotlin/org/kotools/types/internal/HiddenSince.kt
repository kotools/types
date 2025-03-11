package org.kotools.types.internal

/**
 * Specifies the first [version] of Kotools Types where a declaration has been
 * [hidden][DeprecationLevel.HIDDEN] from code.
 */
@InternalKotoolsTypesApi
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS
)
public annotation class HiddenSince(
    /**
     * The first version of Kotools Types where the declaration was hidden from
     * sources.
     */
    val version: KotoolsTypesVersion
)
