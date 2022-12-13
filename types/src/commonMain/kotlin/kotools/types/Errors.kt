package kotools.types

internal object EmptyCollectionError :
    IllegalArgumentException("Given collection shouldn't be empty.")

internal object EmptyMapError :
    IllegalArgumentException("Given map shouldn't be empty.")

internal inline fun <T> tryOrNull(block: () -> T): T? = try {
    block()
} catch (_: RuntimeException) {
    null
}
