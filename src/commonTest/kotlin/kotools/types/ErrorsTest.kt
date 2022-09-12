package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertNotNull
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ErrorsTest {
    @Test
    fun indexOutOfBounds_should_throw_an_IndexOutOfBoundsException() {
        // GIVEN
        val index: Int = Random.nextInt()
        val size: Int = Random.nextInt()
        // WHEN
        val error: IndexOutOfBoundsException =
            assertFailsWith { indexOutOfBounds(index, size) }
        // THEN
        error.message.assertNotNull()
    }

    @Test
    fun indexOutOfBoundsMessage_should_pass() {
        // GIVEN
        val index: Int = Random.nextInt()
        val size: Int = Random.nextInt()
        // WHEN
        val result: String = indexOutOfBoundsMessage(index, size)
        // THEN
        result assertEquals "Index: $index, Size: $size"
    }
}
