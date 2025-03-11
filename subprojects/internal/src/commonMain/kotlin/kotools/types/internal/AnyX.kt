@file:JvmName("AnyExtensions")

package kotools.types.internal

import org.kotools.types.internal.InternalKotoolsTypesApi
import kotlin.jvm.JvmName

/** Returns a unique hash code value for the specified objects. */
@InternalKotoolsTypesApi
public fun hashCodeOf(first: Any, vararg others: Any): Int {
    val prime = 31
    var result: Int = prime + first.hashCode()
    others.forEach { result = prime * result + it.hashCode() }
    return result
}
