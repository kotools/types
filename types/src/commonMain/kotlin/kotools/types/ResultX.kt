package kotools.types

// ---------- Success ----------

internal inline fun <A, B> A.toSuccessfulResult(builder: (A) -> B): Result<B> {
    val value: B = builder(this)
    return Result.success(value)
}

// ---------- Failure ----------

internal fun <A : Throwable> A.toFailure(): Result<Nothing> =
    Result.failure(this)
