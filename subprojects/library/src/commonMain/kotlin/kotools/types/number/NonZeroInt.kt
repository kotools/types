package kotools.types.number

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotools.types.collection.NotEmptySet
import kotools.types.collection.notEmptySetOf
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.ErrorMessage
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.intSerializer
import kotools.types.internal.serializationError
import kotools.types.internal.simpleNameOf
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Since

/**
 * Returns this number as an encapsulated [NonZeroInt], which may involve
 * rounding or truncation, or returns an encapsulated [IllegalArgumentException]
 * if this number equals [zero][ZeroInt].
 */
@OptIn(InternalKotoolsTypesApi::class)
@Since(KotoolsTypesVersion.V4_1_0)
public fun Number.toNonZeroInt(): Result<NonZeroInt> {
    val value: Int = toInt()
    return when {
        value.isStrictlyPositive() -> value.toStrictlyPositiveInt()
        value.isStrictlyNegative() -> value.toStrictlyNegativeInt()
        else -> {
            val message: ErrorMessage = ErrorMessage.zeroNumber
            val exception = IllegalArgumentException("$message")
            Result.failure(exception)
        }
    }
}

/**
 * Represents an integer number of type [Int] that is other than zero.
 *
 * You can use the [toNonZeroInt] function for creating an instance of this
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
 * SAMPLE: NonZeroIntKotlinSample.serialization.md
 * </details>
 */
@OptIn(InternalKotoolsTypesApi::class)
@Serializable(NonZeroIntSerializer::class)
@Since(KotoolsTypesVersion.V1_1_0)
public sealed interface NonZeroInt : AnyInt {
    /** Contains declarations for holding or building a [NonZeroInt]. */
    public companion object {
        /** The minimum value a [NonZeroInt] can have. */
        public val min: StrictlyNegativeInt by lazy(
            StrictlyNegativeInt.Companion::min
        )

        /** The maximum value a [NonZeroInt] can have. */
        public val max: StrictlyPositiveInt by lazy(
            StrictlyPositiveInt.Companion::max
        )

        /**
         * Creates a [NonZeroInt] from the specified [number], which may involve
         * rounding or truncation, or throws an [IllegalArgumentException] if
         * the [number] equals zero.
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
         * val number: NonZeroInt = NonZeroInt.create(23)
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
         * final NonZeroInt number = NonZeroInt.Companion.create(23);
         * System.out.println(number); // 23
         * ```
         * </details>
         * <br>
         *
         * You can use the [NonZeroInt.Companion.createOrNull] function for
         * returning `null` instead of throwing an exception in case of invalid
         * [number].
         */
        @ExperimentalKotoolsTypesApi
        @ExperimentalSince(KotoolsTypesVersion.V4_5_0)
        public fun create(number: Number): NonZeroInt {
            val result: NonZeroInt? = createOrNull(number)
            return requireNotNull(result, ErrorMessage.Companion::zeroNumber)
        }

        /**
         * Creates a [NonZeroInt] from the specified [number], which may involve
         * rounding or truncation, or returns `null` if the [number] equals
         * zero.
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
         * val number: NonZeroInt? = NonZeroInt.createOrNull(23)
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
         * final NonZeroInt number = NonZeroInt.Companion.createOrNull(23);
         * System.out.println(number); // 23
         * ```
         * </details>
         * <br>
         *
         * You can use the [NonZeroInt.Companion.create] function for throwing
         * an exception instead of returning `null` in case of invalid [number].
         */
        @ExperimentalKotoolsTypesApi
        @ExperimentalSince(KotoolsTypesVersion.V4_5_0)
        public fun createOrNull(number: Number): NonZeroInt? {
            val value: Int = number.toInt()
            return when {
                value.isStrictlyPositive() -> StrictlyPositiveInt.create(value)
                value.isStrictlyNegative() -> StrictlyNegativeInt.create(value)
                else -> null
            }
        }

        /** Returns a random [NonZeroInt]. */
        @Since(KotoolsTypesVersion.V3_0_0)
        public fun random(): NonZeroInt {
            val ranges: NotEmptySet<IntRange> = notEmptySetOf(
                min.toInt()..StrictlyNegativeInt.max.toInt(),
                StrictlyPositiveInt.min.toInt()..max.toInt()
            )
            return ranges.toSet()
                .random()
                .random()
                .toNonZeroInt()
                .getOrThrow()
        }
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
public operator fun Int.div(other: NonZeroInt): Int = this / other.toInt()

/**
 * Calculates the remainder of truncating division of this integer by the
 * [other] one.
 */
@Since(KotoolsTypesVersion.V4_1_0)
public operator fun Int.rem(other: NonZeroInt): Int = this % other.toInt()

@InternalKotoolsTypesApi
private object NonZeroIntSerializer : KSerializer<NonZeroInt> by intSerializer(
    NonZeroIntDeserializationStrategy,
    intConverter = { it.toInt() }
)

@InternalKotoolsTypesApi
private object NonZeroIntDeserializationStrategy :
    DeserializationStrategy<NonZeroInt> {
    override val descriptor: SerialDescriptor by lazy {
        val simpleName: String = simpleNameOf<NonZeroInt>()
        val serialName = "${KotoolsTypesPackage.Number}.$simpleName"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.INT)
    }

    override fun deserialize(decoder: Decoder): NonZeroInt {
        val value: Int = decoder.decodeInt()
        return value.toNonZeroInt()
            .getOrNull()
            ?: serializationError(ErrorMessage.zeroNumber)
    }
}
