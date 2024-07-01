@file:JvmName("AnyIntFactory")

package kotools.types.experimental

import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.unexpectedCreationError
import kotools.types.number.AnyInt
import kotools.types.number.ZeroInt
import kotools.types.number.toStrictlyNegativeInt
import kotools.types.number.toStrictlyPositiveInt
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import kotlin.jvm.JvmName

/**
 * Creates an instance of [AnyInt] with the specified [value].
 *
 * <br>
 * <details open>
 * <summary>
 *     <b>Calling from Kotlin</b>
 * </summary>
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * SAMPLE: AnyIntFactoryKotlinSample.constructorLikeInt.md
 * </details>
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Calling from Java</b>
 * </summary>
 *
 * The Java function generated from this one is `AnyIntFactory.create`.
 * Here's an example of calling it from Java code:
 *
 * SAMPLE: [kotools.types.experimental.AnyIntFactoryJavaSample.constructorLikeInt]
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@JvmName("create")
@OptIn(InternalKotoolsTypesApi::class)
public fun AnyInt(value: Int): AnyInt {
    if (value == 0) return ZeroInt
    val result: Result<AnyInt> =
        if (value > 0) value.toStrictlyPositiveInt()
        else value.toStrictlyNegativeInt()
    return result.getOrNull() ?: unexpectedCreationError<AnyInt>(value)
}
