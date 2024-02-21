package kotools.types.number

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.ErrorMessage
import kotools.types.internal.ExperimentalSince
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

@InternalKotoolsTypesApi
@JvmSynthetic
internal fun Int.isStrictlyPositive(): Boolean = this > 0

@InternalKotoolsTypesApi
@JvmSynthetic
@OptIn(ExperimentalKotoolsTypesApi::class)
internal fun StrictlyPositiveInt(number: Number): StrictlyPositiveInt =
    StrictlyPositiveInt.create(number)

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

        /**
         * Creates a [StrictlyPositiveInt] from the specified [number], or
         * throws an [IllegalArgumentException] if the [number] is less than or
         * equals zero.
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * ```kotlin
         * val number: StrictlyPositiveInt = StrictlyPositiveInt.create(42)
         * println(number) // 42
         * ```
         *
         * The [StrictlyPositiveInt] type being an
         * [inline value class](https://kotlinlang.org/docs/inline-classes.html),
         * this function is not available yet for Java users.
         *
         * You can use the [StrictlyPositiveInt.Companion.createOrNull] function
         * for returning `null` instead of throwing an exception in case of
         * invalid [number].
         */
        @ExperimentalKotoolsTypesApi
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmSynthetic
        public fun create(number: Number): StrictlyPositiveInt {
            val result: StrictlyPositiveInt? = createOrNull(number)
            return requireNotNull(result) { number.shouldBeStrictlyPositive() }
        }

        /**
         * Creates a [StrictlyPositiveInt] from the specified [number], or
         * returns `null` if the [number] is less than or equals zero.
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * ```kotlin
         * val number: StrictlyPositiveInt? =
         *     StrictlyPositiveInt.createOrNull(42)
         * println(number) // 42
         * ```
         *
         * The [StrictlyPositiveInt] type being an
         * [inline value class](https://kotlinlang.org/docs/inline-classes.html),
         * this function is not available yet for Java users.
         *
         * You can use the [StrictlyPositiveInt.Companion.create] function for
         * throwing an exception instead of returning `null` in case of invalid
         * [number].
         */
        @ExperimentalKotoolsTypesApi
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmSynthetic
        public fun createOrNull(number: Number): StrictlyPositiveInt? = number
            .toInt()
            .takeIf(Int::isStrictlyPositive)
            ?.let(::StrictlyPositiveInt)

        /** Returns a random [StrictlyPositiveInt]. */
        @Since(KotoolsTypesVersion.V3_0_0)
        public fun random(): StrictlyPositiveInt = (min.value..max.value)
            .random()
            .toStrictlyPositiveInt()
            .getOrThrow()
    }

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toInt(): Int = value

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toString(): String = "$value"
}

@InternalKotoolsTypesApi
internal object StrictlyPositiveIntSerializer :
    KSerializer<StrictlyPositiveInt> by intSerializer(
        StrictlyPositiveIntDeserializationStrategy,
        intConverter = { it.toInt() }
    )

private object StrictlyPositiveIntDeserializationStrategy :
    DeserializationStrategy<StrictlyPositiveInt> {
    @OptIn(InternalKotoolsTypesApi::class)
    override val descriptor: SerialDescriptor by lazy {
        val simpleName: String = simpleNameOf<StrictlyPositiveInt>()
        val serialName = "${KotoolsTypesPackage.Number}.$simpleName"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.INT)
    }

    @OptIn(InternalKotoolsTypesApi::class)
    override fun deserialize(decoder: Decoder): StrictlyPositiveInt {
        val value: Int = decoder.decodeInt()
        return value.toStrictlyPositiveInt().getOrElse {
            val message: ErrorMessage = value.shouldBeStrictlyPositive()
            serializationError(message)
        }
    }
}
