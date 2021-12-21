package io.github.kotools.csv

import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ReaderTest {
    @Nested
    inner class Regular {
        @Test
        fun `should pass`(): Unit = reader {
            file = "test"
            folder = "folder"
        }?.size assertNotNullOrEquals 0

        @Test
        fun `should fail with blank file name`(): Unit = reader { }.assertNull()

        @Test
        fun `should fail with nonexistent file`(): Unit =
            reader { file = "unknown" }.assertNull()
    }

    @Nested
    inner class Strict {
        @Test
        fun `should pass`(): Unit = strictReader {
            file = "test"
            folder = "folder"
        }.size assertNotEquals 0

        @Test
        fun `should fail with blank file name`() {
            assertFailsWith<InvalidPropertyError> {
                strictReader { }
            }
        }

        @Test
        fun `should fail with nonexistent file`() {
            assertFailsWith<FileNotFoundError> {
                strictReader { file = "unknown" }
            }
        }
    }
}
