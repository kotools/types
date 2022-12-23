package kotools.types.number

internal sealed class NumberErrorDescription(private val value: String) {
    override fun toString(): String = value

    object NonZero : NumberErrorDescription("other than zero")
    object Positive : NumberErrorDescription("positive")
    object Negative : NumberErrorDescription("negative")
    object StrictlyPositive : NumberErrorDescription("strictly positive")
    object StrictlyNegative : NumberErrorDescription("strictly negative")
}

internal val otherThanZero = NumberErrorDescription.NonZero
internal val aPositiveNumber = NumberErrorDescription.Positive
internal val aNegativeNumber = NumberErrorDescription.Negative
internal val aStrictlyPositiveNumber = NumberErrorDescription.StrictlyPositive
internal val aStrictlyNegativeNumber = NumberErrorDescription.StrictlyNegative

internal infix fun <N : Number> N.shouldBe(
    description: NumberErrorDescription
): IllegalArgumentException = IllegalArgumentException(
    "Given number should be $description (tried with $this)."
)
