package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.text.NotBlankString
import kotools.types.text.asNotBlankString

/**
 * Representation of negative integers including [zero][ZeroInt].
 *
 * See the [asNegativeInt] property for building a [NegativeInt].
 */
@Serializable(NegativeIntSerializer::class)
@SinceKotoolsTypes("1.1")
public sealed interface NegativeInt : AnyInt {
    /** Contains declarations for holding or building a [NegativeInt]. */
    public companion object {
        /** The minimum value a [NegativeInt] can have. */
        public val min: StrictlyNegativeInt by lazy(
            Int.MIN_VALUE.asStrictlyNegativeInt::getOrThrow
        )

        /** The maximum value a [NegativeInt] can have. */
        public val max: ZeroInt = ZeroInt

        /** Returns a random [NegativeInt]. */
        @SinceKotoolsTypes("3.0")
        public fun random(): NegativeInt = (min.asInt..max.asInt).random()
            .asNegativeInt
            .getOrThrow()
    }
}

/**
 * Returns this integer as a [NegativeInt], or returns an
 * [IllegalArgumentException] if this integer is
 * [strictly positive][StrictlyPositiveInt].
 */
@SinceKotoolsTypes("4.0")
public val Int.asNegativeInt: Result<NegativeInt>
    get() = when {
        this == ZeroInt.asInt -> Result.success(ZeroInt)
        this < ZeroInt.asInt -> asStrictlyNegativeInt
        else -> Result.failure(this shouldBe aNegativeNumber)
    }

internal object NegativeIntSerializer : AnyIntSerializer<NegativeInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.NegativeInt"::asNotBlankString
    )

    override fun deserialize(value: Int): NegativeInt = value.asNegativeInt
        .getOrNull()
        ?: throw SerializationException(value shouldBe aNegativeNumber)
}
