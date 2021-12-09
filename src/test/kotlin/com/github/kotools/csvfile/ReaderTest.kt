package com.github.kotools.csvfile

import com.github.kotools.csvfile.api.csvReader
import com.github.kotools.csvfile.core.semicolon
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

internal class ReaderTest {
    @Test
    internal fun `should pass with empty file`(): Unit = runBlocking {
        csvReader { file = "empty.csv" }?.size assertEquals 0
    }

    @Test
    internal fun `should pass with semicolons as separator`(): Unit =
        runBlocking {
            csvReader {
                file = "test-semicolon.csv"
                separator = semicolon
            }?.size assertNotNullOrEquals 0
        }

    @Test
    internal fun `should pass without file's extension`(): Unit = runBlocking {
        csvReader { file = "test" }?.size assertNotNullOrEquals 0
    }

    @Test
    fun `should fail with nonexistent file`(): Unit = runBlocking {
        csvReader { file = "unknown" }
            .assertNull()
    }

    @Test
    internal fun `should fail without giving file`(): Unit = runBlocking {
        csvReader {
            debug = true
            separator = semicolon
        }.assertNull()
    }
}
