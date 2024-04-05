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
public annotation class ExperimentalSince(val version: KotoolsTypesVersion)

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
public annotation class Since(val version: KotoolsTypesVersion)

/**
 * Specifies the first versions of Kotools Types where a declaration has
 * appeared as a **deprecated** feature.
 *
 * The specified [warningSince] should match the version where the declaration
 * was deprecated with a [warning][DeprecationLevel.WARNING] level.
 * The specified [errorSince] should match the version where it was deprecated
 * with an [error][DeprecationLevel.ERROR] level.
 * The specified [hiddenSince] should match the version where the declaration
 * was deprecated with a [hidden][DeprecationLevel.HIDDEN] level.
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
public annotation class DeprecatedSince(
    val warningSince: KotoolsTypesVersion = KotoolsTypesVersion.Unreleased,
    val errorSince: KotoolsTypesVersion = KotoolsTypesVersion.Unreleased,
    val hiddenSince: KotoolsTypesVersion = KotoolsTypesVersion.Unreleased
)
