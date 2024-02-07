package kotools.types.number

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.Since
import kotools.types.internal.intSerializer
import kotools.types.internal.serializationError
import kotools.types.internal.shouldBeStrictlyNegative
import kotools.types.internal.simpleNameOf
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
internal fun Int.isStrictlyNegative(): Boolean = this < 0

/**
 * Returns this number as an encapsulated [StrictlyNegativeInt], which may
 * involve rounding or truncation, or returns an encapsulated
 * [IllegalArgumentException] if this number is [positive][PositiveInt].
 */
@Since(KotoolsTypesVersion.V4_1_0)
public fun Number.toStrictlyNegativeInt(): Result<StrictlyNegativeInt> =
    runCatching { StrictlyNegativeInt orFail this.toInt() }

/** Represents an integer number of type [Int] that is less than zero. */
@JvmInline
@Serializable(StrictlyNegativeIntSerializer::class)
@Since(KotoolsTypesVersion.V1_1_0)
public value class StrictlyNegativeInt private constructor(
    private val value: Int
) : NonZeroInt, NegativeInt {
    init {
        require(value.isStrictlyNegative()) { value.shouldBeStrictlyNegative() }
    }

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toInt(): Int = value

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toString(): String = "$value"

    /**
     * Contains declarations for holding or building a [StrictlyNegativeInt].
     */
    public companion object {
        /** The minimum value a [StrictlyNegativeInt] can have. */
        public val min: StrictlyNegativeInt by lazy(
            Int.MIN_VALUE.toStrictlyNegativeInt()::getOrThrow
        )

        /** The maximum value a [StrictlyNegativeInt] can have. */
        public val max: StrictlyNegativeInt by lazy(
            (-1).toStrictlyNegativeInt()::getOrThrow
        )

        @JvmSynthetic
        internal infix fun orFail(value: Int): StrictlyNegativeInt =
            StrictlyNegativeInt(value)

        /** Returns a random [StrictlyNegativeInt]. */
        @Since(KotoolsTypesVersion.V3_0_0)
        public fun random(): StrictlyNegativeInt = (min.value..max.value)
            .random()
            .toStrictlyNegativeInt()
            .getOrThrow()
    }
}

internal object StrictlyNegativeIntSerializer :
    KSerializer<StrictlyNegativeInt> by intSerializer(
        StrictlyNegativeIntDeserializationStrategy,
        intConverter = { it.toInt() }
    )

private object StrictlyNegativeIntDeserializationStrategy :
    DeserializationStrategy<StrictlyNegativeInt> {
    override val descriptor: SerialDescriptor by lazy {
        val simpleName: String = simpleNameOf<StrictlyNegativeInt>()
        val serialName = "${KotoolsTypesPackage.Number}.$simpleName"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.INT)
    }

    override fun deserialize(decoder: Decoder): StrictlyNegativeInt {
        val decodeValue: Int = decoder.decodeInt()
        return decodeValue.toStrictlyNegativeInt()
            .getOrNull()
            ?: serializationError(decodeValue.shouldBeStrictlyNegative())
    }
}
