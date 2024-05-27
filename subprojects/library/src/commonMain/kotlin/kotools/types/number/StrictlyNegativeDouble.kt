package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.ErrorMessage
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.deserializationError
import kotools.types.internal.hashCodeOf
import kotools.types.internal.simpleNameOf
import org.kotools.types.internal.ExperimentalSince
import org.kotools.types.internal.KotoolsTypesVersion

/**
 * Represents a floating-point number of type [Double] that is less than zero.
 *
 * You can use the [StrictlyNegativeDouble.Companion.create] or the
 * [StrictlyNegativeDouble.Companion.createOrNull] functions for creating an
 * instance of this type.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Serialization and deserialization</b>
 * </summary>
 *
 * The [serialization and the deserialization processes](https://kotlinlang.org/docs/serialization.html)
 * of this type should behave like for the [Double] type.
 *
 * Here's an example of Kotlin code that encodes and decodes this type using the
 * [JavaScript Object Notation (JSON) format from kotlinx.serialization](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json):
 *
 * SAMPLE: StrictlyNegativeDoubleKotlinSample.serialization.md
 * </details>
 */
@ExperimentalKotoolsTypesApi
@ExperimentalSince(KotoolsTypesVersion.V4_5_0)
@OptIn(InternalKotoolsTypesApi::class)
@Serializable(StrictlyNegativeDoubleSerializer::class)
public class StrictlyNegativeDouble private constructor(
    private val value: Double
) {
    /** Contains static declarations for the [StrictlyNegativeDouble] type. */
    public companion object {
        /**
         * Creates a [StrictlyNegativeDouble] from the specified [number], which
         * may involve rounding or truncation, or throws an
         * [IllegalArgumentException] if the [number] is greater than or equals
         * zero.
         *
         * <br/>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: StrictlyNegativeDoubleCompanionKotlinSample.create.md
         * </details>
         *
         * <br/>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this function from Java code:
         *
         * SAMPLE: StrictlyNegativeDoubleCompanionJavaSample.create.md
         * </details>
         * <br/>
         *
         * You can use the [StrictlyNegativeDouble.Companion.createOrNull] for
         * returning `null` instead of throwing an exception in case of invalid
         * [number].
         */
        public fun create(number: Number): StrictlyNegativeDouble {
            val result: StrictlyNegativeDouble? = createOrNull(number)
            return requireNotNull(result) {
                ErrorMessage.shouldBeLessThanZero(number)
            }
        }

        /**
         * Creates a [StrictlyNegativeDouble] from the specified [number], which
         * may involve rounding or truncation, or returns `null` if the [number]
         * is greater than or equals zero.
         *
         * <br/>
         * <details open>
         * <summary>
         *     <b>Calling from Kotlin</b>
         * </summary>
         *
         * Here's an example of calling this function from Kotlin code:
         *
         * SAMPLE: StrictlyNegativeDoubleCompanionKotlinSample.createOrNull.md
         * </details>
         *
         * <br/>
         * <details>
         * <summary>
         *     <b>Calling from Java</b>
         * </summary>
         *
         * Here's an example of calling this function from Java code:
         *
         * SAMPLE: StrictlyNegativeDoubleCompanionJavaSample.createOrNull.md
         * </details>
         * <br/>
         *
         * You can use the [StrictlyNegativeDouble.Companion.create] function
         * for throwing an exception instead of returning `null` in case of
         * invalid [number].
         */
        public fun createOrNull(number: Number): StrictlyNegativeDouble? {
            val value: Double = number.toDouble()
            return if (value < 0) StrictlyNegativeDouble(value) else null
        }
    }

    // -------------------------- Structural equality --------------------------

    /**
     * Returns `true` if the [other] object is a [StrictlyNegativeDouble] having
     * the same value as this floating-point number, or returns `false`
     * otherwise.
     *
     * <br/>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * SAMPLE: StrictlyNegativeDoubleKotlinSample.equalsOverride.md
     * </details>
     *
     * <br/>
     * <details>
     * <summary>
     *     <b>Calling from Java</b>
     * </summary>
     *
     * Here's an example of calling this function from Java code:
     *
     * SAMPLE: StrictlyNegativeDoubleJavaSample.equalsOverride.md
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun equals(other: Any?): Boolean =
        other is StrictlyNegativeDouble && other.value == this.value

    /**
     * Returns a hash code value for this floating-point number.
     *
     * <br/>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * ```kotlin
     * val number: Number = -23
     * val first: StrictlyNegativeDouble = StrictlyNegativeDouble.create(number)
     * val second: StrictlyNegativeDouble =
     *     StrictlyNegativeDouble.create(number)
     * val result: Boolean = first.hashCode() == second.hashCode()
     * println(result) // true
     * ```
     * </details>
     *
     * <br/>
     * <details>
     * <summary>
     *     <b>Calling from Java</b>
     * </summary>
     *
     * Here's an example of calling this function from Java code:
     *
     * ```java
     * final int number = -23;
     * final StrictlyNegativeDouble first =
     *         StrictlyNegativeDouble.Companion.create(number);
     * final StrictlyNegativeDouble second =
     *         StrictlyNegativeDouble.Companion.create(number);
     * final boolean result = first.hashCode() == second.hashCode();
     * System.out.println(result); // true
     * ```
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun hashCode(): Int = hashCodeOf(value)

    // ------------------------------ Conversions ------------------------------

    /**
     * Returns this floating-point number as [Double].
     *
     * <br/>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * ```kotlin
     * val number: StrictlyNegativeDouble = StrictlyNegativeDouble.create(-7)
     * val result: Double = number.toDouble()
     * println(result) // -7.0
     * ```
     * </details>
     *
     * <br/>
     * <details>
     * <summary>
     *     <b>Calling from Java</b>
     * </summary>
     *
     * Here's an example of calling this function from Java code:
     *
     * ```java
     * final StrictlyNegativeDouble number =
     *         StrictlyNegativeDouble.Companion.create(-7);
     * final double result = number.toDouble();
     * System.out.println(result); // -7.0
     * ```
     * </details>
     */
    public fun toDouble(): Double = value

    /**
     * Returns the string representation of this floating-point number.
     *
     * <br/>
     * <details open>
     * <summary>
     *     <b>Calling from Kotlin</b>
     * </summary>
     *
     * Here's an example of calling this function from Kotlin code:
     *
     * ```kotlin
     * val number: StrictlyNegativeDouble = StrictlyNegativeDouble.create(-23)
     * val result = "$number" // or number.toString()
     * println(result) // -23.0
     * ```
     * </details>
     *
     * <br/>
     * <details>
     * <summary>
     *     <b>Calling from Java</b>
     * </summary>
     *
     * Here's an example of calling this function from Java code:
     *
     * ```java
     * final StrictlyNegativeDouble number =
     *         StrictlyNegativeDouble.Companion.create(-23);
     * final String result = number.toString();
     * System.out.println(result); // -23.0
     * ```
     * </details>
     */
    @Suppress("RedundantModalityModifier")
    final override fun toString(): String = "$value"
}

@InternalKotoolsTypesApi
@OptIn(ExperimentalKotoolsTypesApi::class)
internal object StrictlyNegativeDoubleSerializer :
    KSerializer<StrictlyNegativeDouble> {
    override val descriptor: SerialDescriptor by lazy {
        val type: String = simpleNameOf<StrictlyNegativeDouble>()
        val serialName = "${KotoolsTypesPackage.Number}.$type"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.DOUBLE)
    }

    override fun serialize(encoder: Encoder, value: StrictlyNegativeDouble) {
        val valueAsDouble: Double = value.toDouble()
        encoder.encodeDouble(valueAsDouble)
    }

    override fun deserialize(decoder: Decoder): StrictlyNegativeDouble {
        val number: Double = decoder.decodeDouble()
        return StrictlyNegativeDouble.createOrNull(number)
            ?: deserializationError<StrictlyNegativeDouble>(number)
    }
}
