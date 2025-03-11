package org.kotools.types.internal

/**
 * Specifies the first [version] of Kotools Types where a declaration has
 * appeared as a **stable** feature.
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
public annotation class Since(
    /**
     * The first version of Kotools Types where the declaration was stabilized.
     */
    val version: KotoolsTypesVersion
)
