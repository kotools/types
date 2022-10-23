package kotools.types.number

import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotools.types.assertEquals
import kotools.types.mapSecond
import kotools.types.pairWith
import kotools.types.runMapSecond
import kotlin.test.Test
import kotlin.test.assertFailsWith

class NonZeroHolderDslTest {
    @Test
    fun int_should_pass_with_a_non_zero_Int(): Unit = NonZeroInt.random()
        .value
        .pairWith(nonZero::int)
        .mapSecond(NonZeroInt::value)
        .assertEquals()

    @Test
    fun int_should_throw_an_error_with_zero() {
        assertFailsWith<IllegalArgumentException> { nonZero int 0 }
    }

    @Test
    fun intOrNull_should_pass_with_a_non_zero_Int(): Unit = NonZeroInt.random()
        .value
        .pairWith(nonZero::intOrNull)
        .runMapSecond { assertNotNull().value }
        .assertEquals()

    @Test
    fun intOrNull_should_return_null_with_zero(): Unit = nonZero.intOrNull(0)
        .assertNull()
}

class PositiveHolderDslTest {
    @Test
    fun int_should_pass_with_a_positive_Int(): Unit = PositiveInt.random()
        .value
        .pairWith(positive::int)
        .mapSecond(PositiveInt::value)
        .assertEquals()

    @Test
    fun int_should_throw_an_error_with_a_strictly_negative_Int(): Unit =
        StrictlyNegativeInt.random().run {
            assertFailsWith<IllegalArgumentException> { positive int value }
        }

    @Test
    fun intOrNull_should_pass_with_a_positive_Int(): Unit = PositiveInt.random()
        .value
        .pairWith(positive::intOrNull)
        .runMapSecond { assertNotNull().value }
        .assertEquals()

    @Test
    fun intOrNull_should_return_null_with_a_strictly_negative_Int(): Unit =
        StrictlyNegativeInt.random()
            .value
            .let(positive::intOrNull)
            .assertNull()
}
