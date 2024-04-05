@file:JvmName("AnyIntFactory")

package kotools.types.experimental

import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.unexpectedCreationError
import kotools.types.number.AnyInt
import kotools.types.number.ZeroInt
import kotools.types.number.toStrictlyNegativeInt
import kotools.types.number.toStrictlyPositiveInt
import kotlin.jvm.JvmName

/**
 * Creates an instance of [AnyInt] with the specified [value].
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * ```kotlin
 * val number = AnyInt(1)
 * println(number) // 1
 * ```
 *
 * The Java function generated from this one is `AnyIntFactory.create`.
 * Here's an example of calling it from Java code:
 *
 * ```java
 * AnyInt number = AnyIntFactory.create(1);
 * System.out.println(number); // 1
 * ```
 */
@ExperimentalKotoolsTypesApi
@JvmName("create")
@OptIn(InternalKotoolsTypesApi::class)
public fun AnyInt(value: Int): AnyInt {
    if (value == 0) return ZeroInt
    val result: Result<AnyInt> =
        if (value > 0) value.toStrictlyPositiveInt()
        else value.toStrictlyNegativeInt()
    return result.getOrNull() ?: unexpectedCreationError<AnyInt>(value)
}
