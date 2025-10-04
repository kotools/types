package org.kotools.types

import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion

/**
 * Represents an [integer](https://en.wikipedia.org/wiki/Integer) that is
 * greater than zero.
 *
 * Contrarily to the integer types provided by Kotlin ([Byte], [Short], [Int]
 * and [Long]), this type has no maximum value and can hold greater values than
 * [Long.MAX_VALUE].
 *
 * For creating an instance of this type, see the factory functions provided by
 * the [PositiveInteger.Companion] type.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V5_0_2)
public class PositiveInteger private constructor() {
    /** Contains static declarations for the [PositiveInteger] type. */
    public companion object
}
