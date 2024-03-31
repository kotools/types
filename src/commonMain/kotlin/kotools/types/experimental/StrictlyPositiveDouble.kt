package kotools.types.experimental

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.hashCodeOf
import kotools.types.internal.serializationError
import kotools.types.internal.shouldBeGreaterThanZero
import org.kotools.types.ExperimentalSince
import org.kotools.types.KotoolsTypesVersion
import kotlin.jvm.JvmSynthetic

/**
 * Returns this number as an encapsulated [StrictlyPositiveDouble],
 * which may involve rounding or truncation, or returns an encapsulated
 * [IllegalArgumentException] if this number is negative.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
public fun Number.toStrictlyPositiveDouble(): Result<StrictlyPositiveDouble> =
    runCatching {
        val value: Double = toDouble()
        StrictlyPositiveDouble.orThrow(value)
    }

/**
 * Represents a floating-point number of type [Double] that is greater than
 * zero.
 *
 * You can use the [toStrictlyPositiveDouble] function for creating an instance
 * of this type.
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_4_0)
@OptIn(InternalKotoolsTypesApi::class)
@Serializable(StrictlyPositiveDoubleSerializer::class)
public class StrictlyPositiveDouble private constructor(
    private val value: Double
) : Comparable<StrictlyPositiveDouble> {
    init {
        require(value > 0) { value.shouldBeGreaterThanZero() }
    }

    /**
     * Returns `true` if the [other] object is a [StrictlyPositiveDouble] having
     * the same value as this floating-point number, or returns `false`
     * otherwise.
     */
    override fun equals(other: Any?): Boolean =
        other is StrictlyPositiveDouble && other.value == value

    /**
     * Compares this floating-point number with the [other] one for order.
     * Returns zero if this floating-point number equals the [other] one, a
     * negative number if it's less than the [other] one, or a positive number
     * if it's greater than the [other] one.
     */
    override fun compareTo(other: StrictlyPositiveDouble): Int {
        val x: Double = toDouble()
        val y: Double = other.toDouble()
        return x.compareTo(y)
    }

    /** Returns a hash code value for this floating-point number. */
    override fun hashCode(): Int = hashCodeOf(value)

    /** Returns this floating-point number as a [Double]. */
    public fun toDouble(): Double = value

    /** Returns this floating-point number as [String]. */
    override fun toString(): String = "$value"

    /** Contains static declarations for the [StrictlyPositiveDouble] type. */
    public companion object {
        @JvmSynthetic
        internal fun orThrow(value: Double): StrictlyPositiveDouble =
            StrictlyPositiveDouble(value)
    }
}

@ExperimentalKotoolsTypesApi
@InternalKotoolsTypesApi
internal object StrictlyPositiveDoubleSerializer :
    KSerializer<StrictlyPositiveDouble> {
    override val descriptor: SerialDescriptor by lazy {
        PrimitiveSerialDescriptor(
            serialName = "kotools.types.experimental.StrictlyPositiveDouble",
            PrimitiveKind.DOUBLE
        )
    }

    override fun serialize(encoder: Encoder, value: StrictlyPositiveDouble) {
        val x: Double = value.toDouble()
        encoder.encodeDouble(x)
    }

    override fun deserialize(decoder: Decoder): StrictlyPositiveDouble {
        val value: Double = decoder.decodeDouble()
        return value.toStrictlyPositiveDouble()
            .getOrNull()
            ?: serializationError(value.shouldBeGreaterThanZero())
    }
}
