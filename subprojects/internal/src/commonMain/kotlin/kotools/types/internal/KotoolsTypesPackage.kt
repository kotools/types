/*
 * Copyright 2023 Kotools S.A.S.U.
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

    /**
     * Returns the string representation of this package.
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * ```kotlin
     * val result: String = KotoolsTypesPackage.Number.toString()
     * println(result) // kotools.types.number
     * ```
     *
     * Here's an example of calling this function from Java code:
     *
     * ```java
     * final String result = KotoolsTypesPackage.Number.toString();
     * System.out.println(result); // kotools.types.number
     * ```
     */
    override fun toString(): String = value
}