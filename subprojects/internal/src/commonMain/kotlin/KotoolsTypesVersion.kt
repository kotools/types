/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.internal

/** Represents a version of Kotools Types. */
public enum class KotoolsTypesVersion {
    V4_3_2,
    V4_2_0;

    /** Returns the string representation of this version. */
    override fun toString(): String = name.drop(1)
        .replace('_', '.')
}
