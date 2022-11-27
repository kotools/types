package kotools.types

internal fun <T> T.toSuccessfulResult(): Result<T> = Result.success(this)
