package org.kotools.types.internal

import kotlin.jvm.JvmSynthetic

/** Creates an instance of [PlatformInteger] from the specified [number]. */
@InternalKotoolsTypesApi
public expect fun PlatformInteger(number: Long): PlatformInteger

/**
 * Creates an instance of [PlatformInteger] from the specified [text], or throws
 * an [IllegalArgumentException] if the [text] doesn't represent an integer.
 *
 * The [text] parameter must only contain an optional plus (`+`) or minus
 * (`-`) sign, followed by a sequence of digits.
 */
@InternalKotoolsTypesApi
public expect fun PlatformInteger(text: String): PlatformInteger

/** Represents an integer with a platform-specific implementation. */
@InternalKotoolsTypesApi
public interface PlatformInteger {
    /** Adds the [other] integer to this one. */
    public operator fun plus(other: PlatformInteger): PlatformInteger

    /** Subtracts the [other] integer from this one. */
    public operator fun minus(other: PlatformInteger): PlatformInteger

    /** Multiplies this integer by the [other] one. */
    public operator fun times(other: PlatformInteger): PlatformInteger

    /** Contains class-level declarations for the [PlatformInteger] type. */
    public companion object {
        @JvmSynthetic
        internal fun requirements(text: String) {
            require(text.isNotBlank()) { "Integer should not be blank" }
            val isNumericText: Boolean = text.removePrefix("+")
                .removePrefix("-")
                .all(Char::isDigit)
            require(isNumericText) {
                "Integer can only contain an optional + or - sign, followed " +
                        "by a sequence of digits, was: $text"
            }
        }
    }
}
