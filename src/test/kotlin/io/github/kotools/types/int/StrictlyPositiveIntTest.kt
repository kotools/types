package io.github.kotools.types.int

import io.github.kotools.assert.assertFails
import io.github.kotools.assert.assertNotNull
import io.github.kotools.assert.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test

class StrictlyPositiveIntTest {
    @Nested
    inner class Create {
        @Test
        fun `should pass`(): Unit = assertDoesNotThrow { 1.strictlyPositive }

        @Test
        fun `should fail`(): Unit = assertFails { 0.strictlyPositive }
    }

    @Nested
    inner class CreateOrNull {
        @Test
        fun `should pass`() {
            1.strictlyPositiveOrNull.assertNotNull()
        }

        @Test
        fun `should fail`(): Unit = 0.strictlyPositiveOrNull.assertNull()
    }
}
