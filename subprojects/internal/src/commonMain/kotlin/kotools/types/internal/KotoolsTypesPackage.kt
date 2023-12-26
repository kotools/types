/*
 * Copyright 2023 Kotools S.A.S.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.internal

/** Represents a package of Kotools Types. */
public enum class KotoolsTypesPackage {
    Experimental, Number, Text;

    /** Returns the string representation of this package. */
    override fun toString(): String = "kotools.types.${name.lowercase()}"
}
