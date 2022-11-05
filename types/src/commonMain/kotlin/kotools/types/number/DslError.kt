package kotools.types.number

import kotools.types.SinceKotoolsTypes
import kotools.types.string.NotBlankString

/** Error returned or thrown when creating a number holder fails. */
@SinceKotoolsTypes("3.2")
public sealed class NumberDslError(message: NotBlankString) :
    IllegalArgumentException(message.value)

/** Error returned or thrown when creating a non-zero number fails. */
@SinceKotoolsTypes("3.2")
public sealed class NonZeroNumberDslError : NumberDslError(
    NotBlankString("Given value shouldn't equal 0.")
)

internal object NonZeroNumberDslErrorImplementation : NonZeroNumberDslError()

/** Error returned or thrown when creating a positive number fails. */
@SinceKotoolsTypes("3.2")
public sealed class PositiveNumberDslError(value: Int) : NumberDslError(
    NotBlankString("Given value should be positive (tried with $value).")
)

internal class PositiveNumberDslErrorImplementation(value: Int) :
    PositiveNumberDslError(value)
