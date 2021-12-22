package io.github.kotools.csv.old

import io.github.kotools.csv.assertNotNull
import io.github.kotools.csv.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertFailsWith

class WriterTest {
    private val header: Set<String> by lazy { setOf("h1", "h2") }
    private val rows: List<Map<String, String>> by lazy {
        listOf(mapOf(header.first() to "a", header.last() to "b"))
    }

    @Nested
    inner class Regular {
        @Test
        fun `should pass`(): Unit = writer {
            file = "test"
            folder = "folder"
            header = this@WriterTest.header
            separator = semicolon
            rows { +this@WriterTest.rows.first().values }
        }.assertNotNull()

        @Test
        fun `should fail with blank file name`(): Unit = writer {
            header = this@WriterTest.header
            rows { +this@WriterTest.rows.first().values }
        }.assertNull()

        @Test
        fun `should fail with empty header`(): Unit = writer {
            file = "test"
            rows { +this@WriterTest.rows.first().values }
        }.assertNull()

        @Test
        fun `should fail with empty rows`(): Unit = writer {
            file = "test"
            header = this@WriterTest.header
        }.assertNull()
    }

    @Nested
    inner class Strict {
        @Test
        fun `should pass`(): Unit = assertDoesNotThrow {
            strictWriter {
                file = "test"
                folder = "folder"
                header = this@WriterTest.header
                separator = semicolon
                rows { +this@WriterTest.rows.first().values }
            }
        }

        @Test
        fun `should fail with blank file name`() {
            assertFailsWith<InvalidPropertyError> {
                strictWriter {
                    header = this@WriterTest.header
                    rows { +this@WriterTest.rows.first().values }
                }
            }
        }

        @Test
        fun `should fail with empty header`() {
            assertFailsWith<InvalidPropertyError> {
                strictWriter {
                    file = "test"
                    rows { +this@WriterTest.rows.first().values }
                }
            }
        }

        @Test
        fun `should fail with empty rows`() {
            assertFailsWith<InvalidConfigError> {
                strictWriter {
                    file = "test"
                    header = this@WriterTest.header
                }
            }
        }
    }
}
