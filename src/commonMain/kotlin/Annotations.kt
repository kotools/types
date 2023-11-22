/*
 * Copyright 2022-2023 Loïc Lamarque, Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types

import kotlin.annotation.AnnotationRetention.BINARY
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
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
internal annotation class ExperimentalSinceKotoolsTypes(val version: String)

/**
 * Specifies the first [version] of Kotools Types where a declaration has
 * appeared as a **stable** feature.
 *
 * The [version] should be in the following formats: `<major>.<minor>` or
 * `<major>.<minor>.<patch>`, where _major_, _minor_ and _patch_ are positive
 * integers without leading zeros.
 */
@MustBeDocumented
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
internal annotation class SinceKotoolsTypes(val version: String)
