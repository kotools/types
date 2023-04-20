package kotools.types.result

import kotools.types.number.*
import kotools.types.shouldEqual
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class FlatMapTest {
    @Test
    fun should_pass_with_a_successful_receiver_and_result_of_transformation() {
        val receiver: Result<NonZeroInt> = 1.toNonZeroInt()
        val result: Result<StrictlyPositiveInt> =
            receiver.flatMap { it.plus(2).toStrictlyPositiveInt() }
        result.getOrThrow().toInt() shouldEqual 3
    }

    @Test
    fun should_fail_with_a_failure_as_receiver() {
        val receiver: Result<NonZeroInt> = 0.toNonZeroInt()
        val result: Result<StrictlyPositiveInt> =
            receiver.flatMap { it.minus(2).toStrictlyPositiveInt() }
        val originalException: Throwable =
            assertNotNull(receiver.exceptionOrNull())
        val resultException: Throwable = assertNotNull(result.exceptionOrNull())
        originalException shouldEqual resultException
    }

    @Test
    fun should_fail_with_a_failure_as_result_of_transformation() {
        val receiver: Result<NonZeroInt> = 1.toNonZeroInt()
        val result: Result<StrictlyPositiveInt> =
            receiver.flatMap { it.minus(2).toStrictlyPositiveInt() }
        assertNull(receiver.exceptionOrNull())
        assertFails(result::getOrThrow)
    }
}
