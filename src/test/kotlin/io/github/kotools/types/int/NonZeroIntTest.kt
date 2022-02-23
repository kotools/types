package io.github.kotools.types.int

import io.github.kotools.assert.assertFails
import io.github.kotools.assert.assertNotNull
import io.github.kotools.assert.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test

class NonZeroIntTest {
    @Nested
    inner class Create {
        @Test
        fun `should pass`(): Unit = assertDoesNotThrow { NonZeroInt create 1 }

        @Test
        fun `should fail`(): Unit = assertFails(NonZeroInt.ERROR_MESSAGE) {
            NonZeroInt create 0
        }
    }

    @Nested
    inner class CreateOrNull {
        @Test
        fun `should pass`() {
            NonZeroInt.createOrNull(1)
                .assertNotNull()
        }

        @Test
        fun `should fail`(): Unit = NonZeroInt.createOrNull(0)
            .assertNull(NonZeroInt.ERROR_MESSAGE)
    }
}
