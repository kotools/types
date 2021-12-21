package io.github.kotools.csv

import io.github.kotools.csv.api.CsvFileNotFoundError
import io.github.kotools.csv.api.InvalidPropertyError
import io.github.kotools.csv.api.semicolon
import io.github.kotools.csv.utils.assertNotEquals
import io.github.kotools.csv.utils.assertNotNullOrEquals
import io.github.kotools.csv.utils.assertNull
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
            separator = semicolon
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
            separator = semicolon
        }.size assertNotEquals 0

        @Test
        fun `should fail with blank file name`() {
            assertFailsWith<InvalidPropertyError> {
                strictReader { }
            }
        }

        @Test
        fun `should fail with nonexistent file`() {
            assertFailsWith<CsvFileNotFoundError> {
                strictReader { file = "unknown" }
            }
        }
    }
}
