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
import kotools.types.internal.shouldBePositive
import kotools.types.internal.simpleNameOf
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Since

/**
 * Returns this number as an encapsulated [PositiveInt], which may involve
 * rounding or truncation, or returns an encapsulated [IllegalArgumentException]
 * if this number is [strictly negative][StrictlyNegativeInt].
 */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_1_0)
public fun Number.toPositiveInt(): Result<PositiveInt> {
    val value: Int = toInt()
    return when {
        value == 0 -> Result.success(ZeroInt)
        value.isStrictlyPositive() -> value.toStrictlyPositiveInt()
        else -> {
            val message: ErrorMessage = value.shouldBePositive()
            val exception = IllegalArgumentException("$message")
            Result.failure(exception)
        }
    }
}

/**
 * Represents an integer number of type [Int] that is greater than or equals
 * zero.
 *
 * You can use the [toPositiveInt] function for creating an instance of this
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
 * val number: PositiveInt = 123.toPositiveInt()
 *     .getOrThrow()
 * val encoded: String = Json.encodeToString(number)
 * println(encoded) // 123
 * val decoded: PositiveInt = Json.decodeFromString(encoded)
 * println(decoded == number) // true
 * ```
 * </details>
 */
@OptIn(InternalKotoolsTypesApi::class)
@Serializable(PositiveIntSerializer::class)
@Since(KotoolsTypesVersion.V1_1_0)
public sealed interface PositiveInt : AnyInt {
    /** Contains declarations for holding or building a [PositiveInt]. */
    public companion object {
        /** The minimum value a [PositiveInt] can have. */
        public val min: ZeroInt = ZeroInt

        /** The maximum value a [PositiveInt] can have. */
        public val max: StrictlyPositiveInt by lazy { StrictlyPositiveInt.max }

        /**
         * Creates a [PositiveInt] from the specified [number], which may
         * involve rounding or truncation, or throws an
         * [IllegalArgumentException] if the [number] is less than zero.
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
         * val number: PositiveInt = PositiveInt.create(23)
         * println(number) // 23
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
         * final PositiveInt number = PositiveInt.Companion.create(23);
         * System.out.println(number); // 23
         * ```
         * </details>
         * <br>
         *
         * You can use the [PositiveInt.Companion.createOrNull] function for
         * returning `null` instead of throwing an exception in case of invalid
         * [number].
         */
        @ExperimentalKotoolsTypesApi
        @ExperimentalSince(KotoolsTypesVersion.V4_5_0)
        public fun create(number: Number): PositiveInt {
            val result: PositiveInt? = createOrNull(number)
            return requireNotNull(result, number::shouldBePositive)
        }

        /**
         * Creates a [PositiveInt] from the specified [number], which may
         * involve rounding or truncation, or returns `null` if the [number] is
         * less than zero.
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
         * val number: PositiveInt? = PositiveInt.createOrNull(23)
         * println(number) // 23
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
         * final PositiveInt number = PositiveInt.Companion.createOrNull(23);
         * System.out.println(number); // 23
         * ```
         * </details>
         * <br>
         *
         * You can use the [PositiveInt.Companion.create] function for throwing
         * an exception instead of returning `null` in case of invalid [number].
         */
        @ExperimentalKotoolsTypesApi
        @ExperimentalSince(KotoolsTypesVersion.V4_5_0)
        public fun createOrNull(number: Number): PositiveInt? {
            val value: Int = number.toInt()
            return when {
                value == 0 -> ZeroInt
                value.isStrictlyPositive() -> StrictlyPositiveInt.create(value)
                else -> null
            }
        }

        /** Returns a random [PositiveInt]. */
        @Since(KotoolsTypesVersion.V3_0_0)
        public fun random(): PositiveInt = (min.toInt()..max.toInt())
            .random()
            .toPositiveInt()
            .getOrThrow()
    }

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toInt(): Int

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toString(): String
}

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to [zero][ZeroInt].
 */
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun PositiveInt.div(other: StrictlyPositiveInt): PositiveInt {
    val result: Int = toInt() / other
    return result.toPositiveInt()
        .getOrThrow()
}

/**
 * Divides this integer by the [other] one, truncating the result to an integer
 * that is closer to [zero][ZeroInt].
 */
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun PositiveInt.div(other: StrictlyNegativeInt): NegativeInt {
    val result: Int = toInt() / other
    return result.toNegativeInt()
        .getOrThrow()
}

/**
 * Calculates the remainder of truncating division of this integer by the
 * [other] one.
 */
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun PositiveInt.rem(other: NonZeroInt): PositiveInt {
    val result: Int = toInt() % other
    return result.toPositiveInt()
        .getOrThrow()
}

@InternalKotoolsTypesApi
internal object PositiveIntSerializer :
    KSerializer<PositiveInt> by intSerializer(
        PositiveIntDeserializationStrategy,
        intConverter = { it.toInt() }
    )

private object PositiveIntDeserializationStrategy :
    DeserializationStrategy<PositiveInt> {
    @OptIn(InternalKotoolsTypesApi::class)
    override val descriptor: SerialDescriptor by lazy {
        val simpleName: String = simpleNameOf<PositiveInt>()
        val serialName = "${KotoolsTypesPackage.Number}.$simpleName"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.INT)
    }

    @OptIn(InternalKotoolsTypesApi::class)
    override fun deserialize(decoder: Decoder): PositiveInt {
        val value: Int = decoder.decodeInt()
        return value.toPositiveInt().getOrElse {
            val message: ErrorMessage = value.shouldBePositive()
            serializationError(message)
        }
    }
}
