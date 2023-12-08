/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types

private val regex: Regex by lazy { Regex("^[a-z]+(\\.[a-z]+)*\$") }

/** Enumerates packages of Kotools Types. */
public enum class KotoolsTypesPackage(private val value: String) {
    Root("kotools.types"),
    Internal("$Root.internal");

    init {
        require(value matches regex) {
            "Package should match the regular expression $regex"
        }
    }

    /** Returns the string representation of this package. */
    override fun toString(): String = value
}
