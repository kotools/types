package kotools.types.result

import kotools.types.shouldEqual
import kotlin.random.Random
import kotlin.test.Test

class ResultContextTest {
    @Test
    fun resultOf_should_pass() {
        val value: Int = Random.nextInt()
        val result: Result<Int> = resultOf { value }
        result.getOrThrow() shouldEqual value
    }
}
