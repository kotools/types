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
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesPackage
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
@OptIn(ExperimentalKotoolsTypesApi::class)
public fun Number.toStrictlyNegativeInt(): Result<StrictlyNegativeInt> =
    runCatching(StrictlyNegativeInt.Companion::create)

/**
 * Represents an integer number of type [Int] that is less than zero.
 *
 * You can use the [toStrictlyNegativeInt] function for creating an instance of
 * this type.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Serialization and deserialization</b>
 * </summary>
 *
 * The [serialization and deserialization processes](https://kotlinlang.org/docs/serialization.html)
 * of this type behave like for the [Int] type.
 *
 * Here's an example of Kotlin code that encodes and decodes this type using the
 * [JavaScript Object Notation (JSON) format from kotlinx.serialization](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json):
 *
 * ```kotlin
 * val number: StrictlyNegativeInt = (-123).toStrictlyNegativeInt()
 *     .getOrThrow()
 * val encoded: String = Json.encodeToString(number)
 * println(encoded) // -123
 * val decoded: StrictlyNegativeInt = Json.decodeFromString(encoded)
 * println(decoded == number) // true
 * ```
 * </details>
 */
@JvmInline
@OptIn(InternalKotoolsTypesApi::class)
@Serializable(StrictlyNegativeIntSerializer::class)
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
        @OptIn(ExperimentalKotoolsTypesApi::class)
        public val max: StrictlyNegativeInt by lazy { create(-1) }

        /**
         * Creates a [StrictlyNegativeInt] from the specified [number], which
         * may involve rounding or truncation, or throws an
         * [IllegalArgumentException] if the [number] is greater than or equals
         * zero.
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
        @JvmSynthetic
        public fun create(number: Number): StrictlyNegativeInt {
            val result: StrictlyNegativeInt? = createOrNull(number)
            return requireNotNull(result, number::shouldBeStrictlyNegative)
        }

        /**
         * Creates a [StrictlyNegativeInt] from the specified [number], which
         * may involve rounding or truncation, or returns `null` if the [number]
         * is greater than or equals zero.
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
        @JvmSynthetic
        public fun createOrNull(number: Number): StrictlyNegativeInt? {
            val value: Int = number.toInt()
            val isValid: Boolean = value.isStrictlyNegative()
            return if (isValid) StrictlyNegativeInt(value) else null
        }

        /** Returns a random [StrictlyNegativeInt]. */
        public fun random(): StrictlyNegativeInt = (min.value..max.value)
            .random()
            .toStrictlyNegativeInt()
            .getOrThrow()
    }

    override fun toInt(): Int = value

    override fun toString(): String = "$value"
}

@InternalKotoolsTypesApi
internal object StrictlyNegativeIntSerializer :
    KSerializer<StrictlyNegativeInt> by intSerializer(
        StrictlyNegativeIntDeserializationStrategy,
        intConverter = StrictlyNegativeInt::toInt
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
