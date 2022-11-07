package kotools.types

import kotools.types.string.NotBlankString

internal inline fun <T> KotoolsTypeBuilderResult<T>.onError(
    action: (KotoolsTypeBuilderResult.Error) -> T
): T = when (this) {
    is KotoolsTypeBuilderResult.Error -> action(this)
    is KotoolsTypeBuilderResult.Success -> value
}

internal sealed interface KotoolsTypeBuilderResult<out T> {
    data class Success<out T>(val value: T) : KotoolsTypeBuilderResult<T>
    class Error(message: NotBlankString) :
        IllegalArgumentException(message.value),
        KotoolsTypeBuilderResult<Nothing>
}
