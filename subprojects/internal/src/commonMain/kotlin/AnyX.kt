/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.internal

/** Returns a unique hash code value for the specified objects. */
public fun hashCodeOf(first: Any, vararg others: Any): Int {
    val prime = 31
    var result: Int = prime + first.hashCode()
    others.forEach { result = prime * result + it.hashCode() }
    return result
}
