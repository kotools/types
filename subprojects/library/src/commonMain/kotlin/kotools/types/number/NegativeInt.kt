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
import kotools.types.internal.shouldBeNegative
import kotools.types.internal.simpleNameOf

/**
 * Returns this number as an encapsulated [NegativeInt], which may involve
 * rounding or truncation, or returns an encapsulated [IllegalArgumentException]
 * if this number is [strictly positive][StrictlyPositiveInt].
 */
@OptIn(InternalKotoolsTypesApi::class)
public fun Number.toNegativeInt(): Result<NegativeInt> {
    val value: Int = toInt()
    return when {
        value == 0 -> Result.success(ZeroInt)
        value.isStrictlyNegative() -> value.toStrictlyNegativeInt()
        else -> {
            val message: ErrorMessage = value.shouldBeNegative()
            val exception = IllegalArgumentException("$message")
            Result.failure(exception)
        }
    }
}

/**
 * Represents an integer number of type [Int] that is less than or equals zero.
 *
 * You can use the [toNegativeInt] function for creating an instance of this
 * type.
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
 * val number: NegativeInt = (-123).toNegativeInt()
 *     .getOrThrow()
 * val encoded: String = Json.encodeToString(number)
 * println(encoded) // -123
 * val decoded: NegativeInt = Json.decodeFromString(encoded)
 * println(decoded == number) // true
 * ```
 * </details>
 */
@OptIn(InternalKotoolsTypesApi::class)
@Serializable(NegativeIntSerializer::class)
public sealed interface NegativeInt : AnyInt {
    /** Contains declarations for holding or building a [NegativeInt]. */
    public companion object {
        /** The minimum value a [NegativeInt] can have. */
        public val min: StrictlyNegativeInt by lazy { StrictlyNegativeInt.min }

        /** The maximum value a [NegativeInt] can have. */
        public val max: ZeroInt = ZeroInt

        /**
         * Creates a [NegativeInt] from the specified [number], which may
         * involve rounding or truncation, or throws an
         * [IllegalArgumentException] if the [number] is greater than zero.
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * ```kotlin
         * val number: NegativeInt = NegativeInt.create(-7)
         * println(number) // -7
         * ```
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this function from Java code:
         *
         * ```java
         * final NegativeInt number = NegativeInt.Companion.create(-7);
         * System.out.println(number); // -7
         * ```
         * </details>
         * <br>
         *
         * You can use the [NegativeInt.Companion.createOrNull] function for
         * returning `null` instead of throwing an exception in case of invalid
         * [number].
         */
        @ExperimentalKotoolsTypesApi
        public fun create(number: Number): NegativeInt {
            val result: NegativeInt? = createOrNull(number)
            return requireNotNull(result, number::shouldBeNegative)
        }

        /**
         * Creates a [NegativeInt] from the specified [number], which may
         * involve rounding or truncation, or returns `null` if the [number] is
         * greater than zero.
         *
         * <br>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * ```kotlin
         * val number: NegativeInt? = NegativeInt.createOrNull(-7)
         * println(number) // -7
         * ```
         * </details>
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this function from Java code:
         *
         * ```java
         * final NegativeInt number = NegativeInt.Companion.createOrNull(-7);
         * System.out.println(number); // -7
         * ```
         * </details>
         * <br>
         *
         * You can use the [NegativeInt.Companion.create] function for throwing
         * an exception instead of returning `null` in case of invalid [number].
         */
        @ExperimentalKotoolsTypesApi
        public fun createOrNull(number: Number): NegativeInt? {
            val value: Int = number.toInt()
            return when {
                value == 0 -> ZeroInt
                value.isStrictlyNegative() -> StrictlyNegativeInt.create(value)
                else -> null
            }
        }

        /** Returns a random [NegativeInt]. */
        public fun random(): NegativeInt = (min.toInt()..max.toInt())
            .random()
            .toNegativeInt()
            .getOrThrow()
    }

    override fun toInt(): Int

    override fun toString(): String
}

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to [zero][ZeroInt].
 */
public operator fun NegativeInt.div(other: StrictlyPositiveInt): NegativeInt {
    val result: Int = toInt() / other
    return result.toNegativeInt()
        .getOrThrow()
}

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to [zero][ZeroInt].
 */
public operator fun NegativeInt.div(other: StrictlyNegativeInt): PositiveInt {
    val result: Int = toInt() / other
    return result.toPositiveInt()
        .getOrThrow()
}

/**
 * Calculates the remainder of truncating division of this integer by the
 * [other] one.
 */
public operator fun NegativeInt.rem(other: NonZeroInt): NegativeInt {
    val result: Int = toInt() % other
    return result.toNegativeInt()
        .getOrThrow()
}

@InternalKotoolsTypesApi
internal object NegativeIntSerializer :
    KSerializer<NegativeInt> by intSerializer(
        NegativeIntDeserializationStrategy,
        intConverter = { it.toInt() }
    )

@InternalKotoolsTypesApi
private object NegativeIntDeserializationStrategy :
    DeserializationStrategy<NegativeInt> {
    override val descriptor: SerialDescriptor by lazy {
        val simpleName: String = simpleNameOf<NegativeInt>()
        val serialName = "${KotoolsTypesPackage.Number}.$simpleName"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.INT)
    }

    override fun deserialize(decoder: Decoder): NegativeInt {
        val value: Int = decoder.decodeInt()
        return value.toNegativeInt()
            .getOrNull()
            ?: serializationError(value.shouldBeNegative())
    }
}
