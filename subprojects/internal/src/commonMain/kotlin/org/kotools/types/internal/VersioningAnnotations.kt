package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi

/**
 * Specifies the first [version] of Kotools Types where a declaration has
 * appeared as an **experimental** feature.
 */
@InternalKotoolsTypesApi
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS
)
public annotation class ExperimentalSince(
    /** The version of Kotools Types where the declaration was introduced. */
    val version: KotoolsTypesVersion
)

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

/**
 * Specifies the first [version] of Kotools Types where a declaration has
 * appeared as a **deprecated** feature with a
 * [warning][DeprecationLevel.WARNING] level.
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
public annotation class DeprecatedAsWarningSince(
    /**
     * The first version of Kotools Types where the declaration was deprecated
     * with a warning level.
     */
    val version: KotoolsTypesVersion
)

/**
 * Specifies the first [version] of Kotools Types where a declaration has
 * appeared as a **deprecated** feature with an [error][DeprecationLevel.ERROR]
 * level.
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
public annotation class DeprecatedAsErrorSince(
    /**
     * The first version of Kotools Types where the declaration was deprecated
     * with an error level.
     */
    val version: KotoolsTypesVersion
)

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
