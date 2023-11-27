/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.internal

/** Represents a version of Kotools Types. */
public enum class KotoolsTypesVersion {
    V1_1_0,
    V3_0_0,
    V4_0_0,
    V4_1_0,
    V4_2_0,
    V4_3_1,
    V4_3_2,
    V4_3_3;

    /**
     * Returns the string representation of this version.
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * ```kotlin
     * val version: KotoolsTypesVersion = KotoolsTypesVersion.V4_0_0
     * val message = "$version" // or version.toString()
     * println(message) // 4.0.0
     * ```
     *
     * Here's an example of calling this function from Java code:
     *
     * ```java
     * KotoolsTypesVersion version = KotoolsTypesVersion.V4_0_0;
     * String message = version.toString();
     * System.out.println(message); // 4.0.0
     * ```
     */
    override fun toString(): String = name.drop(1)
        .replace('_', '.')
}
