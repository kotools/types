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
import kotools.types.internal.shouldBeStrictlyNegative
import kotools.types.internal.simpleNameOf
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmSynthetic

@InternalKotoolsTypesApi
@JvmSynthetic
internal fun Int.isStrictlyNegative(): Boolean = this < 0

/**
 * Returns this number as an encapsulated [StrictlyNegativeInt], which may
 * involve rounding or truncation, or returns an encapsulated
 * [IllegalArgumentException] if this number is [positive][PositiveInt].
 */
@OptIn(ExperimentalKotoolsTypesApi::class, InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_1_0)
public fun Number.toStrictlyNegativeInt(): Result<StrictlyNegativeInt> =
    runCatching(StrictlyNegativeInt.Companion::create)

/** Represents an integer number of type [Int] that is less than zero. */
@JvmInline
@OptIn(InternalKotoolsTypesApi::class)
@Serializable(StrictlyNegativeIntSerializer::class)
@Since(KotoolsTypesVersion.V1_1_0)
public value class StrictlyNegativeInt private constructor(
    private val value: Int
) : NonZeroInt, NegativeInt {
    /**
     * Contains declarations for holding or building a [StrictlyNegativeInt].
     */
    public companion object {
        /** The minimum value a [StrictlyNegativeInt] can have. */
        @OptIn(ExperimentalKotoolsTypesApi::class)
        public val min: StrictlyNegativeInt by lazy { create(Int.MIN_VALUE) }

        /** The maximum value a [StrictlyNegativeInt] can have. */
        public val max: StrictlyNegativeInt by lazy(
            (-1).toStrictlyNegativeInt()::getOrThrow
        )

        /**
         * Creates a [StrictlyNegativeInt] from the specified [number], or
         * throws an [IllegalArgumentException] if the [number] is greater than
         * or equals zero.
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * ```kotlin
         * val number: StrictlyNegativeInt = StrictlyNegativeInt.create(-7)
         * println(number) // -7
         * ```
         *
         * The [StrictlyNegativeInt] type being an
         * [inline value class](https://kotlinlang.org/docs/inline-classes.html),
         * this function is not available yet for Java users.
         *
         * You can use the [StrictlyNegativeInt.Companion.createOrNull] function
         * for returning `null` instead of throwing an exception in case of
         * invalid [number].
         */
        @ExperimentalKotoolsTypesApi
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmSynthetic
        public fun create(number: Number): StrictlyNegativeInt {
            val result: StrictlyNegativeInt? = this.createOrNull(number)
            return requireNotNull(result) { number.shouldBeStrictlyNegative() }
        }

        /**
         * Creates a [StrictlyNegativeInt] from the specified [number], or
         * returns `null` if the [number] is greater than or equals zero.
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * ```kotlin
         * val number: StrictlyNegativeInt? =
         *     StrictlyNegativeInt.createOrNull(-7)
         * println(number) // -7
         * ```
         *
         * The [StrictlyNegativeInt] type being an
         * [inline value class](https://kotlinlang.org/docs/inline-classes.html),
         * this function is not available yet for Java users.
         *
         * You can use the [StrictlyNegativeInt.Companion.create] function for
         * throwing an exception instead of returning `null` in case of invalid
         * [number].
         */
        @ExperimentalKotoolsTypesApi
        @ExperimentalSince(KotoolsTypesVersion.Unreleased)
        @JvmSynthetic
        public fun createOrNull(number: Number): StrictlyNegativeInt? = number
            .toInt()
            .takeIf(Int::isStrictlyNegative)
            ?.let(::StrictlyNegativeInt)

        /** Returns a random [StrictlyNegativeInt]. */
        @Since(KotoolsTypesVersion.V3_0_0)
        public fun random(): StrictlyNegativeInt = (min.value..max.value)
            .random()
            .toStrictlyNegativeInt()
            .getOrThrow()
    }

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toInt(): Int = value

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toString(): String = "$value"
}

@InternalKotoolsTypesApi
internal object StrictlyNegativeIntSerializer :
    KSerializer<StrictlyNegativeInt> by intSerializer(
        StrictlyNegativeIntDeserializationStrategy,
        intConverter = { it.toInt() }
    )

private object StrictlyNegativeIntDeserializationStrategy :
    DeserializationStrategy<StrictlyNegativeInt> {
    @OptIn(InternalKotoolsTypesApi::class)
    override val descriptor: SerialDescriptor by lazy {
        val simpleName: String = simpleNameOf<StrictlyNegativeInt>()
        val serialName = "${KotoolsTypesPackage.Number}.$simpleName"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.INT)
    }

    @OptIn(InternalKotoolsTypesApi::class)
    override fun deserialize(decoder: Decoder): StrictlyNegativeInt {
        val value: Int = decoder.decodeInt()
        return value.toStrictlyNegativeInt().getOrElse {
            val message: ErrorMessage = value.shouldBeStrictlyNegative()
            serializationError(message)
        }
    }
}
