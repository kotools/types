package kotools.types.internal

import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.annotation.AnnotationTarget.TYPEALIAS

/** Marks a declaration that shouldn't be used outside Kotools Types. */
@MustBeDocumented
@RequiresOptIn(
    "This declaration is internal and shouldn't be used outside Kotools Types."
)
@Retention(BINARY)
@Target(CLASS, FUNCTION)
public annotation class InternalKotoolsTypesApi

// -------------------------- Versioning annotations ---------------------------

/**
 * Specifies the first [version] of Kotools Types where a declaration has
 * appeared as an **experimental** feature.
 */
@InternalKotoolsTypesApi
@MustBeDocumented
@Retention(SOURCE)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalSince(val version: KotoolsTypesVersion)

/**
 * Specifies the first [version] of Kotools Types where a declaration has
 * appeared as a **stable** feature.
 */
@InternalKotoolsTypesApi
@MustBeDocumented
@Retention(SOURCE)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
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
@Retention(SOURCE)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class DeprecatedSince(
    val warningSince: KotoolsTypesVersion,
    val errorSince: KotoolsTypesVersion = KotoolsTypesVersion.Unreleased,
    val hiddenSince: KotoolsTypesVersion = KotoolsTypesVersion.Unreleased
)
