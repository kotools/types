package kotools.types.experimental

import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.unexpectedCreationError
import kotools.types.number.NonZeroInt
import kotools.types.number.toNonZeroInt
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import kotlin.jvm.JvmSynthetic

/**
 * Returns the negative of this integer number.
 *
 * Here's an example of calling this function from Kotlin code:
 *
 * SAMPLE: [kotools.types.experimental.NonZeroIntCommonSample.unaryMinusOperator]
 *
 * Please note that this function is not available yet for Java users.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@JvmSynthetic
@OptIn(InternalKotoolsTypesApi::class)
public operator fun NonZeroInt.unaryMinus(): NonZeroInt {
    val value: Int = toInt()
        .unaryMinus()
    return value.toNonZeroInt()
        .getOrNull()
        ?: unexpectedCreationError<NonZeroInt>(value)
}
