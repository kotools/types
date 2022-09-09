package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertNotNull
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ErrorsTest {
    @Test
    fun `indexOutOfBounds should throw an IndexOutOfBoundsException`() {
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
    fun `indexOutOfBoundsMessage should pass`() {
        // GIVEN
        val index: Int = Random.nextInt()
        val size: Int = Random.nextInt()
        // WHEN
        val result: String = indexOutOfBoundsMessage(index, size)
        // THEN
        result assertEquals "Index: $index, Size: $size"
    }
}
