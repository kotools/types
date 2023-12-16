/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.internal

import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.annotation.AnnotationTarget.TYPEALIAS

/**
 * Specifies the first [version] of Kotools Types where a declaration has
 * appeared as an **experimental** feature.
 *
 * The [version] should be in the following formats: `<major>.<minor>` or
 * `<major>.<minor>.<patch>`, where _major_, _minor_ and _patch_ are positive
 * integers without leading zeros.
 */
@MustBeDocumented
@Retention(SOURCE)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalSince(val version: KotoolsTypesVersion)

/**
 * Specifies the first [version] of Kotools Types where a declaration has
 * appeared as a **stable** feature.
 */
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
@MustBeDocumented
@Retention(SOURCE)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class DeprecatedSince(
    val warningSince: KotoolsTypesVersion,
    val errorSince: KotoolsTypesVersion = KotoolsTypesVersion.Unreleased,
    val hiddenSince: KotoolsTypesVersion = KotoolsTypesVersion.Unreleased
)
