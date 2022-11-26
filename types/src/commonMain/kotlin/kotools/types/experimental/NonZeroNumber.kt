package kotools.types.experimental

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel.Experimental
import kotools.types.failureOf
import kotools.types.number.otherThanZero
import kotools.types.number.shouldBe
import kotools.types.toSuccessfulResult

/**
 * Returns this number as a [NonZeroNumber], or an [IllegalArgumentException] if
 * this number equals zero.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public val Byte.nonZero: Result<NonZeroNumber<Byte>>
    get() = toNonZeroNumberIf { it != 0.toByte() }

/**
 * Returns this number as a [NonZeroNumber], or an [IllegalArgumentException] if
 * this number equals zero.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public val Short.nonZero: Result<NonZeroNumber<Short>>
    get() = toNonZeroNumberIf { it != 0.toShort() }

@ExperimentalKotoolsTypesApi
private inline fun <N : Number> N.toNonZeroNumberIf(
    predicate: (N) -> Boolean
): Result<NonZeroNumber<N>> = takeIf(predicate)
    ?.toSuccessfulResult(::NonZeroNumberImplementation)
    ?: failureOf { this shouldBe otherThanZero }

/**
 * Representation of numbers other than zero.
 *
 * @param N The type of [Number] to hold.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Types, "3.2", Experimental)
public sealed interface NonZeroNumber<out N : Number> : ExplicitNumber<N>

@ExperimentalKotoolsTypesApi
private data class NonZeroNumberImplementation<out N : Number>(
    override val value: N
) : NonZeroNumber<N> {
    override fun toString(): String = "$value"
}
