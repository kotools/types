package io.github.kotools.csv

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import kotlin.test.Test

class WriterTest {
    private val header: Set<String> by lazy { setOf("h1", "h2") }
    private val rows: List<Map<String, String>> by lazy {
        listOf(mapOf(header.first() to "a", header.last() to "b"))
    }
    private val validConfiguration: Writer.() -> Unit by lazy {
        {
            file = "test"
            folder = "folder"
            overwrite = true
            separator = semicolon
            header = this@WriterTest.header
            records { +this@WriterTest.rows.first().values }
        }
    }

    @Nested
    inner class CsvWriterOrNull {
        @Test
        fun `should pass`(): Unit = runBlocking {
            csvWriterOrNull(validConfiguration)
                .assertNotNull()
        }

        @Test
        fun `should fail with blank file name`(): Unit = runBlocking {
            csvWriterOrNull {
                header = this@WriterTest.header
                records { +this@WriterTest.rows.first().values }
            }.assertNull()
        }

        @Test
        fun `should fail with empty header`(): Unit = runBlocking {
            csvWriterOrNull {
                file = "test"
                folder = "folder"
                records { +this@WriterTest.rows.first().values }
            }.assertNull()
        }

        @Test
        fun `should fail with empty records`(): Unit = runBlocking {
            csvWriterOrNull {
                file = "test"
                folder = "folder"
                header = this@WriterTest.header
            }.assertNull()
        }
    }

    @Nested
    inner class CsvWriterOrNullAsync {
        @Test
        fun `should pass`(): Unit = runBlocking {
            csvWriterOrNullAsync(validConfiguration)
                .await()
                .assertNotNull()
        }
    }
}
