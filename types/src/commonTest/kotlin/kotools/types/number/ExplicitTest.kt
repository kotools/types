package kotools.types.number

import kotools.assert.assertEquals
import kotlin.random.Random
import kotlin.test.Test

class ExplicitNumberToStringTest {
    @Test
    fun should_behave_like_its_value() {
        val x: ExplicitNumber<Int> = Random.nonZeroInt()
        x.toString() assertEquals x.value.toString()
    }
}
