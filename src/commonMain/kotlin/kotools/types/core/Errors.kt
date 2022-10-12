package kotools.types.core

/**
 * Returns the result of calling the [block] function, or returns `null` if the
 * [block] function throws a [RuntimeException].
 */
internal inline fun <T> tryOrNull(block: () -> T): T? = try {
    block()
} catch (_: RuntimeException) {
    null
}
