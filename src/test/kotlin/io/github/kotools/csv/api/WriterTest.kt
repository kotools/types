package io.github.kotools.csv.api

import io.github.kotools.csv.utils.assertEquals
import io.github.kotools.csv.utils.assertNotNull
import io.github.kotools.csv.utils.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertFailsWith

class WriterTest {
    private val header: Set<String> by lazy { setOf("h1", "h2") }
    private val rows: List<Map<String, String>> by lazy {
        listOf(mapOf(header.first() to "a", header.last() to "b"))
    }

    @Nested
    inner class Normal {
        @Test
        fun `should pass with file in folder`(): Unit = runBlocking {
            val fileValue = "nested"
            val folderValue = "folder"
            csvWriterAsync {
                file = fileValue
                folder = folderValue
                separator = semicolon
                header = this@WriterTest.header
                rows { +this@WriterTest.rows.first().values }
            }.await().assertNotNull()
            csvReader {
                file = fileValue
                folder = folderValue
                separator = semicolon
            } assertEquals rows
        }

        @Test
        fun `should pass with nonexistent file`(): Unit = runBlocking {
            val fileValue = "new-writer-file"
            csvWriter {
                file = fileValue
                header = this@WriterTest.header
                rows { +this@WriterTest.rows.first().values }
            }.assertNotNull()
            csvReader { file = fileValue } assertEquals rows
        }

        @Test
        fun `should fail with blank file name`(): Unit = runBlocking {
            csvWriter {
                header = this@WriterTest.header
                rows { +this@WriterTest.rows.first().values }
            }.assertNull()
            csvWriter {
                file = ""
                header = this@WriterTest.header
                rows { +this@WriterTest.rows.first().values }
            }.assertNull()
        }

        @Test
        fun `should fail without header`(): Unit = runBlocking {
            csvWriter {
                file = "test"
                rows { +this@WriterTest.rows.first().values }
            }.assertNull()
            csvWriter {
                file = "test"
                header = emptySet()
                rows { +this@WriterTest.rows.first().values }
            }.assertNull()
        }

        @Test
        fun `should fail without rows`(): Unit = runBlocking {
            csvWriter {
                file = ""
                header = this@WriterTest.header
            }.assertNull()
            csvWriter {
                file = ""
                header = this@WriterTest.header
                rows { }
            }.assertNull()
        }
    }

    @Nested
    inner class Strict {
        @Test
        fun `should pass`(): Unit = runBlocking {
            val fileValue = "nested"
            val folderValue = "folder"
            strictCsvWriterAsync {
                file = fileValue
                folder = folderValue
                separator = semicolon
                header = this@WriterTest.header
                rows { +this@WriterTest.rows.first().values }
            }.await().assertNotNull()
            strictCsvReader {
                file = fileValue
                folder = folderValue
                separator = semicolon
            } assertEquals rows
        }

        @Test
        fun `should fail with blank file name`(): Unit = runBlocking {
            assertFailsWith<InvalidPropertyError> {
                strictCsvWriter {
                    header = this@WriterTest.header
                    rows { +this@WriterTest.rows.first().values }
                }
            }
            assertFailsWith<InvalidPropertyError> {
                strictCsvWriter {
                    file = ""
                    header = this@WriterTest.header
                    rows { +this@WriterTest.rows.first().values }
                }
            }
        }

        @Test
        fun `should fail without header`(): Unit = runBlocking {
            assertFailsWith<InvalidPropertyError> {
                strictCsvWriter {
                    file = "test"
                    rows { +this@WriterTest.rows.first().values }
                }
            }
            assertFailsWith<InvalidPropertyError> {
                strictCsvWriter {
                    file = "test"
                    header = emptySet()
                    rows { +this@WriterTest.rows.first().values }
                }
            }
        }

        @Test
        fun `should fail without rows`(): Unit = runBlocking {
            assertFailsWith<InvalidConfigError> {
                strictCsvWriter {
                    file = ""
                    header = this@WriterTest.header
                }
            }
            assertFailsWith<InvalidConfigError> {
                strictCsvWriter {
                    file = ""
                    header = this@WriterTest.header
                    rows { }
                }
            }
        }
    }
}
