/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.internal.text

/** Represents a special character. */
public enum class SpecialChar(private val value: Char) {
    AtSign('@');

    /** Returns this special character as [Char]. */
    public fun toChar(): Char = value

    /** Returns the string representation of this special character. */
    override fun toString(): String = "$value"
}
