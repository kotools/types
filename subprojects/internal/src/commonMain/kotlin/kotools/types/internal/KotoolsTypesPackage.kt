/*
 * Copyright 2023 Kotools S.A.S.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.internal

private val regex: Regex by lazy { Regex("^[a-z]+(\\.[a-z]+)*\$") }

/** Represents a package of Kotools Types. */
public enum class KotoolsTypesPackage(private val value: String) {
    Number("kotools.types.number"),
    Text("kotools.types.text");

    init {
        require(value matches regex) {
            "Package should match the regular expression $regex"
        }
    }

    /** Returns the string representation of this package. */
    override fun toString(): String = value
}
