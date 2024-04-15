package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion

/** Represents the [zero](https://en.wikipedia.org/wiki/0) number. */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.Unreleased)
public object Zero {
    private const val VALUE: Byte = 0

    /**
     * Returns `true` if the specified [number] equals zero.
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: ZeroKotlinSample.isEqualTo_Byte.md
     * </details>
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Java</b>
     * </summary>
     *
     * Here's an example of calling this function from Java code:
     *
     * SAMPLE: ZeroJavaSample.isEqualTo_Byte.md
     * </details>
     */
    public infix fun isEqualTo(number: Byte): Boolean = this.VALUE == number

    /**
     * Returns this number as [Byte].
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: ZeroKotlinSample.toByte.md
     * </details>
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Java</b>
     * </summary>
     *
     * Here's an example of calling this function from Java code:
     *
     * SAMPLE: ZeroJavaSample.toByte.md
     * </details>
     */
    public fun toByte(): Byte = this.VALUE

    /**
     * Returns the string representation of this number.
     *
     * <br>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: ZeroKotlinSample.toStringSample.md
     * </details>
     *
     * <br>
     * <details>
     * <summary>
     *     <b>Calling from Java</b>
     * </summary>
     *
     * Here's an example of calling this function from Java code:
     *
     * SAMPLE: ZeroJavaSample.toStringSample.md
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String = VALUE.toString()
}
