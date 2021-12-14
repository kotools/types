package io.github.kotools.csv.api

import io.github.kotools.csv.core.CsvError
import io.github.kotools.csv.utils.assertEquals
import io.github.kotools.csv.utils.assertNotEquals
import io.github.kotools.csv.utils.assertNotNullOrEquals
import io.github.kotools.csv.utils.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ReaderTest {
    @Nested
    inner class Normal {
        @Test
        fun `should pass with empty file`(): Unit = runBlocking {
            csvReaderAsync { file = "empty.csv" }.await()?.size assertEquals 0
        }

        @Test
        fun `should pass with file in folder`(): Unit = runBlocking {
            csvReader {
                file = "nested.csv"
                folder = "folder"
            }?.size assertNotNullOrEquals 0
        }

        @Test
        fun `should pass with semicolons as separator`(): Unit =
            runBlocking {
                csvReader {
                    file = "test-semicolon.csv"
                    separator = semicolon
                }?.size assertNotNullOrEquals 0
            }

        @Test
        fun `should fail with nonexistent file`(): Unit = runBlocking {
            csvReader { file = "unknown" }.assertNull()
        }

        @Test
        fun `should fail without giving file`(): Unit = runBlocking {
            csvReader { }.assertNull()
        }
    }

    @Nested
    inner class Strict {
        @Test
        fun `should pass`(): Unit = runBlocking {
            strictCsvReaderAsync {
                file = "nested.csv"
                folder = "folder"
            }.await().size assertNotEquals 0
        }

        @Test
        fun `should fail with nonexistent file`(): Unit = runBlocking {
            assertFailsWith<CsvError.FileNotFoundError> {
                strictCsvReader { file = "unknown" }
            }
        }

        @Test
        fun `should fail without giving file`(): Unit = runBlocking {
            assertFailsWith<CsvError.InvalidPropertyError> {
                strictCsvReader { }
            }
        }
    }
}
