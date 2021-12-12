package io.github.kotools.csv.api

import io.github.kotools.csv.utils.assertEquals
import io.github.kotools.csv.utils.assertNotNull
import io.github.kotools.csv.utils.assertNull
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class WriterTest {
    private val header: Set<String> by lazy { setOf("h1", "h2") }
    private val rows: List<Map<String, String>> by lazy {
        listOf(mapOf(header.first() to "a", header.last() to "b"))
    }

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
            file = ""
            header = this@WriterTest.header
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
