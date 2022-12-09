package kotools.types

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.number.aNegativeNumber
import kotools.types.number.shouldBe
import kotlin.jvm.JvmInline

/** Representation of negative integers, including zero. */
@JvmInline
@Serializable(NegativeIntSerializer::class)
@SinceKotools(Types, "3.2")
public value class NegativeInt
private constructor(private val value: Int) : ExplicitInt,
    Comparable<NegativeInt> {
    internal companion object {
        infix fun of(value: Int): Result<NegativeInt> = value.takeIf { it <= 0 }
            ?.toSuccessfulResult(::NegativeInt)
            ?: Result.failure(value shouldBe aNegativeNumber)
    }

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     */
    override fun compareTo(other: NegativeInt): Int =
        value.compareTo(other.value)

    override fun toInt(): Int = value
    override fun toString(): String = "$value"
}

internal object NegativeIntSerializer :
    ExplicitIntSerializer<NegativeInt>(Int::toNegativeInt)

/**
 * Returns this integer as a [NegativeInt], or [IllegalArgumentException] if
 * this integer is strictly positive.
 */
@SinceKotools(Types, "3.2")
public fun Int.toNegativeInt(): Result<NegativeInt> = NegativeInt of this
