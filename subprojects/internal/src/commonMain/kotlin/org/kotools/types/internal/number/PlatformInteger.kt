package org.kotools.types.internal.number

import org.kotools.types.internal.InternalKotoolsTypesApi

/** Returns a [PlatformInteger] representing exactly the specified [value]. */
@InternalKotoolsTypesApi
public expect fun PlatformInteger(value: Long): PlatformInteger

@OptIn(InternalKotoolsTypesApi::class)
internal expect fun PlatformInteger(value: String): PlatformInteger

/** Implementations of this interface represent a platform-specific integer. */
@InternalKotoolsTypesApi
public interface PlatformInteger {
    // ------------------------------- Creations -------------------------------

    /** Contains type-level declarations for [PlatformInteger]. */
    public companion object {
        /**
         * Returns a [PlatformInteger] representing exactly the number described
         * by [value], or throws [NumberFormatException] if the [value] doesn't
         * represent an integer.
         */
        public fun parse(value: String): PlatformInteger {
            if (!this.isValidInteger(value)) throw NumberFormatException(
                "\"$value\" is not a valid integer."
            )
            val sign: String = value.first()
                .takeIf { it == '-' }
                ?.toString()
                ?: ""
            val digits: String = value.removePrefix(sign)
                .trimStart('0')
                .ifEmpty { "0" }
            return PlatformInteger("$sign$digits")
        }

        private fun isValidInteger(value: String): Boolean {
            if (value.isEmpty()) return false
            val firstCharacter: Char = value.first()
            val start: Int =
                if (firstCharacter == '+' || firstCharacter == '-') 1
                else 0
            if (start == value.length) return false
            return value.substring(start)
                .all(Char::isDigit)
        }
    }

    // ------------------------------ Comparisons ------------------------------

    /**
     * Compares this integer with the [other] one for order.
     *
     * Returns a negative number, zero, or a positive number as this integer is
     * less than, equal to, or greater than the [other] one.
     */
    public operator fun compareTo(other: PlatformInteger): Int
}
