package kotools.types.number

import kotools.assert.*
import kotlin.test.Test

class IntBuilderTest {
    @Test
    fun intOrNull_should_pass_with_a_valid_value() {
        val builder: IntBuilder<NonZeroInt> = IntBuilder(::nonZeroInt)
        val value: Int = randomNonZeroInt().value
        val result: NonZeroInt? = builder intOrNull value
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun intOrNull_should_return_null_with_an_invalid_value() {
        val builder: IntBuilder<NonZeroInt> = IntBuilder(::nonZeroInt)
        val result: NonZeroInt? = builder intOrNull 0
        result.assertNull()
    }

    @Test
    fun intOrThrow_should_pass_with_a_valid_value() {
        val builder: IntBuilder<NonZeroInt> = IntBuilder(::nonZeroInt)
        val value: Int = randomNonZeroInt().value
        val result: NonZeroInt = builder intOrThrow value
        result.value assertEquals value
    }

    @Test
    fun intOrThrow_should_throw_an_error_with_an_invalid_value() {
        val builder: IntBuilder<NonZeroInt> = IntBuilder(::nonZeroInt)
        val result: IllegalArgumentException =
            assertFailsWith { builder intOrThrow 0 }
        result.message.assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
