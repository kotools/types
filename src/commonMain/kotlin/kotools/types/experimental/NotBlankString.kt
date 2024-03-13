package kotools.types.experimental

import kotools.types.internal.ExperimentalSince
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString
import kotlin.jvm.JvmSynthetic

/**
 * Concatenates this character with the [other] string.
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * ```kotlin
 * val first = 'a'
 * val second: NotBlankString = " book".toNotBlankString()
 *     .getOrThrow()
 * val result: NotBlankString = first + second
 * println(result) // a book
 * ```
 *
 * Please note that this function is not available yet for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@JvmSynthetic
@OptIn(InternalKotoolsTypesApi::class)
public operator fun Char.plus(other: NotBlankString): NotBlankString =
    plus("$other")
        .toNotBlankString()
        .getOrThrow()
