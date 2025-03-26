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
import kotools.types.internal.shouldBeStrictlyPositive
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
internal fun Int.isStrictlyPositive(): Boolean = this > 0

/**
 * Returns this number as an encapsulated [StrictlyPositiveInt], which may
 * involve rounding or truncation, or returns an encapsulated
 * [IllegalArgumentException] if this number is [negative][NegativeInt].
 */
@Since(KotoolsTypesVersion.V4_1_0)
public fun Number.toStrictlyPositiveInt(): Result<StrictlyPositiveInt> =
    runCatching {
        val number: Int = this.toInt()
        StrictlyPositiveInt.orThrow(number)
    }

/**
 * Represents an integer number of type [Int] that is greater than zero.
 *
 * You can use the [toStrictlyPositiveInt] function for creating an instance of
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
 * SAMPLE: [kotools.types.number.StrictlyPositiveIntCommonSample.serialization]
 * </details>
 */
@JvmInline
@OptIn(InternalKotoolsTypesApi::class)
@Serializable(StrictlyPositiveIntSerializer::class)
@Since(KotoolsTypesVersion.V1_1_0)
public value class StrictlyPositiveInt private constructor(
    private val value: Int
) : NonZeroInt, PositiveInt {
    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toInt(): Int = value

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toString(): String = "$value"

    /**
     * Contains declarations for holding or building a [StrictlyPositiveInt].
     */
    public companion object {
        /** The minimum value a [StrictlyPositiveInt] can have. */
        public val min: StrictlyPositiveInt by lazy { this.orThrow(1) }

        /** The maximum value a [StrictlyPositiveInt] can have. */
        public val max: StrictlyPositiveInt by lazy {
            this.orThrow(Int.MAX_VALUE)
        }

        @JvmSynthetic
        internal fun orThrow(number: Int): StrictlyPositiveInt {
            require(number > 0, number::shouldBeStrictlyPositive)
            return StrictlyPositiveInt(number)
        }

        /**
         * Creates a [StrictlyPositiveInt] from the specified [number], which
         * may involve rounding or truncation, or returns `null` if the [number]
         * is less than or equals zero.
         *
         * <br>
         * <details>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: [kotools.types.number.StrictlyPositiveIntCompanionCommonSample.createOrNull]
         * </details>
         * <br>
         *
         * The [StrictlyPositiveInt] type being an
         * [inline value class](https://kotlinlang.org/docs/inline-classes.html),
         * this function is not available yet for Java users.
         */
        @ExperimentalKotoolsTypesApi
        @ExperimentalSince(KotoolsTypesVersion.V4_5_0)
        @JvmSynthetic
        public fun createOrNull(number: Number): StrictlyPositiveInt? {
            val value: Int = number.toInt()
            val isValid: Boolean = value.isStrictlyPositive()
            return if (isValid) StrictlyPositiveInt(value) else null
        }

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
        intConverter = StrictlyPositiveInt::toInt
    )

private object StrictlyPositiveIntDeserializationStrategy :
    DeserializationStrategy<StrictlyPositiveInt> {
    override val descriptor: SerialDescriptor by lazy {
        val simpleName: String = simpleNameOf<StrictlyPositiveInt>()
        val serialName = "kotools.types.number.$simpleName"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.INT)
    }

    override fun deserialize(decoder: Decoder): StrictlyPositiveInt {
        val value: Int = decoder.decodeInt()
        return value.toStrictlyPositiveInt().getOrElse {
            val message: ErrorMessage = value.shouldBeStrictlyPositive()
            serializationError(message)
        }
    }
}
