package kotools.types.experimental.number

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test

@ExperimentalKotoolsTypesApi
class PositiveIntTest {
    @Test
    fun int_positive_should_pass_with_a_positive_Int(): Unit = PositiveInt.range
        .random()
        .let { it.positive.getOrThrow().value assertEquals it }

    @Test
    fun int_positive_should_fail_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.range.random()
        assertFailsWith<PositiveNumber.Exception>(value.positive::getOrThrow)
            .message.assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
