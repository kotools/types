package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.text.NotBlankString
import kotools.types.text.asNotBlankString
import kotools.types.toSuccessfulResult
import kotlin.jvm.JvmInline

/**
 * Representation of negative integers excluding [zero][ZeroInt].
 *
 * See the [asStrictlyNegativeInt] property for building a
 * [StrictlyNegativeInt].
 */
@JvmInline
@Serializable(StrictlyNegativeIntSerializer::class)
@SinceKotoolsTypes("1.1")
public value class StrictlyNegativeInt
private constructor(override val asInt: Int) : NonZeroInt, NegativeInt {
    /**
     * Contains declarations for holding or building a [StrictlyNegativeInt].
     */
    public companion object {
        /** The minimum value a [StrictlyNegativeInt] can have. */
        public val min: StrictlyNegativeInt by lazy(
            Int.MIN_VALUE.asStrictlyNegativeInt::getOrThrow
        )

        /** The maximum value a [StrictlyNegativeInt] can have. */
        public val max: StrictlyNegativeInt by lazy(
            (-1).asStrictlyNegativeInt::getOrThrow
        )

        internal infix fun of(value: Int): Result<StrictlyNegativeInt> = value
            .takeIf { it < ZeroInt.asInt }
            ?.toSuccessfulResult(::StrictlyNegativeInt)
            ?: Result.failure(value shouldBe aStrictlyNegativeNumber)

        /** Returns a random [StrictlyNegativeInt]. */
        @SinceKotoolsTypes("3.0")
        public fun random(): StrictlyNegativeInt = (min.asInt..max.asInt)
            .random()
            .asStrictlyNegativeInt
            .getOrThrow()
    }

    override fun toString(): String = "$asInt"
}

/**
 * Returns this integer as a [StrictlyNegativeInt], or returns an
 * [IllegalArgumentException] if this integer is [positive][PositiveInt].
 */
@SinceKotoolsTypes("4.0")
public val Int.asStrictlyNegativeInt: Result<StrictlyNegativeInt>
    get() = StrictlyNegativeInt of this

internal object StrictlyNegativeIntSerializer :
    AnyIntSerializer<StrictlyNegativeInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.StrictlyNegativeInt"::asNotBlankString
    )

    override fun deserialize(value: Int): StrictlyNegativeInt = value
        .asStrictlyNegativeInt
        .getOrNull()
        ?: throw SerializationException(value shouldBe aStrictlyNegativeNumber)
}
