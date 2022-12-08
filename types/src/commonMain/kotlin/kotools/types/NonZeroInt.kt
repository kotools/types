package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.number.otherThanZero
import kotools.types.number.shouldBe
import kotlin.jvm.JvmInline

/** Representation of integers other than zero. */
@JvmInline
@SinceKotools(Types, "3.2")
public value class NonZeroInt private constructor(private val value: Int) {
    internal companion object {
        infix fun of(value: Int): Result<NonZeroInt> = value.takeIf { it != 0 }
            ?.toSuccessfulResult(::NonZeroInt)
            ?: Result.failure(value shouldBe otherThanZero)
    }

    /** Returns this value as an [Int]. */
    public fun toInt(): Int = value

    /** Returns this value as a [String]. */
    override fun toString(): String = "$value"
}

/**
 * Returns this integer as a [NonZeroInt], or [IllegalArgumentException] if this
 * integer equals zero.
 */
@SinceKotools(Types, "3.2")
public fun Int.toNonZeroInt(): Result<NonZeroInt> = NonZeroInt of this
