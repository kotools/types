package org.kotools.types.internal

/**
 * Specifies the first [version] of Kotools Types where a declaration has
 * appeared as an **experimental** feature.
 */
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS
)
internal annotation class ExperimentalSince(val version: KotoolsTypesVersion)

/**
 * Specifies the first [version] of Kotools Types where a declaration has
 * appeared as a **stable** feature.
 */
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS
)
internal annotation class Since(val version: KotoolsTypesVersion)
