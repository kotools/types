package kotools.assert

import org.junit.jupiter.api.assertDoesNotThrow

/**
 * Asserts that the [block] function doesn't throw an error and returns its
 * result.
 */
@SinceKotoolsAssert("2.1")
public fun <T> assertPass(block: () -> T): T = assertDoesNotThrow(block)
