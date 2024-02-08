package kotools.types.number

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.Since
import kotools.types.internal.intSerializer
import kotools.types.internal.serializationError
import kotools.types.internal.shouldBeStrictlyPositive
import kotools.types.internal.simpleNameOf
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
internal fun Int.isStrictlyPositive(): Boolean = this > 0

@JvmSynthetic
internal fun StrictlyPositiveInt(number: Number): StrictlyPositiveInt =
    StrictlyPositiveInt orFail number.toInt()

/**
 * Returns this number as an encapsulated [StrictlyPositiveInt], which may
 * involve rounding or truncation, or returns an encapsulated
 * [IllegalArgumentException] if this number is [negative][NegativeInt].
 */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_1_0)
public fun Number.toStrictlyPositiveInt(): Result<StrictlyPositiveInt> =
    runCatching { StrictlyPositiveInt(this) }

/** Represents an integer number of type [Int] that is greater than zero. */
@JvmInline
@OptIn(InternalKotoolsTypesApi::class)
@Serializable(StrictlyPositiveIntSerializer::class)
@Since(KotoolsTypesVersion.V1_1_0)
public value class StrictlyPositiveInt private constructor(
    private val value: Int
) : NonZeroInt, PositiveInt {
    init {
        require(value.isStrictlyPositive()) { value.shouldBeStrictlyPositive() }
    }

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toInt(): Int = value

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toString(): String = "$value"

    /**
     * Contains declarations for holding or building a [StrictlyPositiveInt].
     */
    public companion object {
        /** The minimum value a [StrictlyPositiveInt] can have. */
        public val min: StrictlyPositiveInt by lazy(
            1.toStrictlyPositiveInt()::getOrThrow
        )

        /** The maximum value a [StrictlyPositiveInt] can have. */
        public val max: StrictlyPositiveInt by lazy(
            Int.MAX_VALUE.toStrictlyPositiveInt()::getOrThrow
        )

        @JvmSynthetic
        internal infix fun orFail(value: Int): StrictlyPositiveInt =
            StrictlyPositiveInt(value)

        /** Returns a random [StrictlyPositiveInt]. */
        @Since(KotoolsTypesVersion.V3_0_0)
        public fun random(): StrictlyPositiveInt = (min.value..max.value)
            .random()
            .toStrictlyPositiveInt()
            .getOrThrow()
    }
}

@InternalKotoolsTypesApi
internal object StrictlyPositiveIntSerializer :
    KSerializer<StrictlyPositiveInt> by intSerializer(
        StrictlyPositiveIntDeserializationStrategy,
        intConverter = { it.toInt() }
    )

@InternalKotoolsTypesApi
private object StrictlyPositiveIntDeserializationStrategy :
    DeserializationStrategy<StrictlyPositiveInt> {
    override val descriptor: SerialDescriptor by lazy {
        val simpleName: String = simpleNameOf<StrictlyPositiveInt>()
        val serialName = "${KotoolsTypesPackage.Number}.$simpleName"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.INT)
    }

    override fun deserialize(decoder: Decoder): StrictlyPositiveInt {
        val value: Int = decoder.decodeInt()
        return value.toStrictlyPositiveInt()
            .getOrNull()
            ?: serializationError(value.shouldBeStrictlyPositive())
    }
}
