/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.internal

/**
 * Specifies the first [version] of Kotools Types where a declaration has
 * appeared as an **experimental** feature.
 *
 * The [version] should be in the following formats: `<major>.<minor>` or
 * `<major>.<minor>.<patch>`, where _major_, _minor_ and _patch_ are positive
 * integers without leading zeros.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS
)
public annotation class ExperimentalSince(val version: KotoolsTypesVersion)

/**
 * Specifies the first [version] of Kotools Types where a declaration has
 * appeared as a **stable** feature.
 *
 * The [version] should be in the following formats: `<major>.<minor>` or
 * `<major>.<minor>.<patch>`, where _major_, _minor_ and _patch_ are positive
 * integers without leading zeros.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS
)
public annotation class Since(val version: KotoolsTypesVersion)
