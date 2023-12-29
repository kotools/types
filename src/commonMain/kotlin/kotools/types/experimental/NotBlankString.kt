/*
 * Copyright 2023 Kotools S.A.S.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotools.types.internal.ExperimentalSince
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.unexpectedCreationError
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString
import kotlin.jvm.JvmSynthetic

/**
 * Concatenates this string with the [other] one.
 *
 * Here's an example for calling this function from Kotlin code:
 *
 * ```kotlin
 * val first: NotBlankString = "hello".toNotBlankString()
 *     .getOrThrow()
 * val second = " world"
 * val result: NotBlankString = first + second
 * println(result) // hello world
 * ```
 *
 * Please note that this function is currently not available for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
@JvmSynthetic
public operator fun NotBlankString.plus(other: String): NotBlankString {
    val value: String = toString() + other
    return value.toNotBlankString()
        .getOrNull()
        ?: unexpectedCreationError<NotBlankString>(value)
}

/**
 * Concatenates this string with the [other] one.
 *
 * Here's an example for calling this function from Kotlin code:
 *
 * ```kotlin
 * val first: NotBlankString = "hello".toNotBlankString()
 *     .getOrThrow()
 * val second: NotBlankString = " world".toNotBlankString()
 *     .getOrThrow()
 * val result: NotBlankString = first + second
 * println(result) // hello world
 * ```
 *
 * Please note that this function is currently not available for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
@JvmSynthetic
public operator fun NotBlankString.plus(other: NotBlankString): NotBlankString =
    plus("$other")

/**
 * Concatenates this string with the [other] character.
 *
 * Here's an example for calling this function from Kotlin code:
 *
 * ```kotlin
 * val first: NotBlankString = "hell".toNotBlankString()
 *     .getOrThrow()
 * val result: NotBlankString = first + 'o'
 * println(result) // hello
 * ```
 *
 * Please note that this function is currently not available for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
@JvmSynthetic
public operator fun NotBlankString.plus(other: Char): NotBlankString =
    plus("$other")
