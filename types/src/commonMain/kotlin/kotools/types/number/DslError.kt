package kotools.types.number

import kotools.types.SinceKotoolsTypes
import kotools.types.string.NotBlankString
import kotools.types.string.toNotBlankString

/** Error returned or thrown when creating a number holder fails. */
@SinceKotoolsTypes("3.2")
public sealed class NumberDslError(message: NotBlankString) :
    IllegalArgumentException(message.value)

/** Error returned or thrown when creating a non-zero number fails. */
@SinceKotoolsTypes("3.2")
public sealed class NonZeroDslError : NumberDslError(
    "NonZeroInt doesn't accept 0.".toNotBlankString()
)

internal object NonZeroDslErrorImplementation : NonZeroDslError()
