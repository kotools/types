package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.text.NotBlankString
import kotools.types.text.asNotBlankString

/**
 * Representation of positive integers including [zero][ZeroInt].
 *
 * See the [asPositiveInt] property for building a [PositiveInt].
 */
@Serializable(PositiveIntSerializer::class)
@SinceKotoolsTypes("1.1")
public sealed interface PositiveInt : AnyInt {
    /** Contains declarations for holding or building a [PositiveInt]. */
    public companion object {
        /** The minimum value a [PositiveInt] can have. */
        public val min: ZeroInt = ZeroInt

        /** The maximum value a [PositiveInt] can have. */
        public val max: StrictlyPositiveInt by lazy(
            StrictlyPositiveInt.Companion::max
        )

        /** Returns a random [PositiveInt]. */
        @SinceKotoolsTypes("3.0")
        public fun random(): PositiveInt =
            setOf(ZeroInt, StrictlyPositiveInt.random()).random()
    }
}

/**
 * Returns this integer as a [PositiveInt], or returns an
 * [IllegalArgumentException] if this integer is
 * [strictly negative][StrictlyNegativeInt].
 */
@SinceKotoolsTypes("4.0")
public val Int.asPositiveInt: Result<PositiveInt>
    get() = when {
        this == ZeroInt.asInt -> Result.success(ZeroInt)
        this > ZeroInt.asInt -> asStrictlyPositiveInt
        else -> Result.failure(this shouldBe aPositiveNumber)
    }

internal object PositiveIntSerializer : AnyIntSerializer<PositiveInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.PositiveInt"::asNotBlankString
    )

    override fun deserialize(value: Int): PositiveInt = value.asPositiveInt
        .getOrNull()
        ?: throw SerializationException(value shouldBe aPositiveNumber)
}
