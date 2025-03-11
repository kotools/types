package kotools.types.number

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotools.types.internal.ErrorMessage
import kotools.types.internal.intSerializer
import kotools.types.internal.serializationError
import kotools.types.internal.shouldBeStrictlyNegative
import kotools.types.internal.simpleNameOf
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.InternalKotoolsTypesApi
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Since
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
@Since(KotoolsTypesVersion.V4_1_0)
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
 * SAMPLE: [kotools.types.number.StrictlyNegativeIntCommonSample.serialization]
 * </details>
 */
@JvmInline
@OptIn(InternalKotoolsTypesApi::class)
@Serializable(StrictlyNegativeIntSerializer::class)
@Since(KotoolsTypesVersion.V1_1_0)
public value class StrictlyNegativeInt private constructor(
    private val value: Int
) : NonZeroInt, NegativeInt {
    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toInt(): Int = value

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toString(): String = "$value"

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
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [kotools.types.number.StrictlyNegativeIntCompanionCommonSample.create]
         * </details>
         * <br>
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
        @ExperimentalSince(KotoolsTypesVersion.V4_5_0)
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
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [kotools.types.number.StrictlyNegativeIntCompanionCommonSample.createOrNull]
         * </details>
         * <br>
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
        @ExperimentalSince(KotoolsTypesVersion.V4_5_0)
        @JvmSynthetic
        public fun createOrNull(number: Number): StrictlyNegativeInt? {
            val value: Int = number.toInt()
            val isValid: Boolean = value.isStrictlyNegative()
            return if (isValid) StrictlyNegativeInt(value) else null
        }

        /** Returns a random [StrictlyNegativeInt]. */
        @Since(KotoolsTypesVersion.V3_0_0)
        public fun random(): StrictlyNegativeInt = (min.value..max.value)
            .random()
            .toStrictlyNegativeInt()
            .getOrThrow()
    }

}

@InternalKotoolsTypesApi
internal object StrictlyNegativeIntSerializer :
    KSerializer<StrictlyNegativeInt> by intSerializer(
        StrictlyNegativeIntDeserializationStrategy,
        intConverter = StrictlyNegativeInt::toInt
    )

private object StrictlyNegativeIntDeserializationStrategy :
    DeserializationStrategy<StrictlyNegativeInt> {
    override val descriptor: SerialDescriptor by lazy {
        val simpleName: String = simpleNameOf<StrictlyNegativeInt>()
        val serialName = "kotools.types.number.$simpleName"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.INT)
    }

    override fun deserialize(decoder: Decoder): StrictlyNegativeInt {
        val value: Int = decoder.decodeInt()
        return value.toStrictlyNegativeInt().getOrElse {
            val message: ErrorMessage = value.shouldBeStrictlyNegative()
            serializationError(message)
        }
    }
}
