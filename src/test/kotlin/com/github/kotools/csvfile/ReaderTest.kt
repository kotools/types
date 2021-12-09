package com.github.kotools.csvfile

import com.github.kotools.csvfile.api.csv
import com.github.kotools.csvfile.api.reader
import com.github.kotools.csvfile.core.semicolon
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

internal class ReaderTest {
    @Test
    internal fun `should pass with empty file`(): Unit = runBlocking {
        csv.reader { file = "empty.csv" }?.size assertEquals 0
    }

    @Test
    internal fun `should pass with semicolons as separator`(): Unit =
        runBlocking {
            csv.reader {
                file = "test-semicolon.csv"
                separator = semicolon
            }?.size assertNotNullOrEquals 0
        }

    @Test
    internal fun `should pass without file's extension`(): Unit = runBlocking {
        csv.reader { file = "test" }?.size assertNotNullOrEquals 0
    }

    @Test
    fun `should fail with nonexistent file`(): Unit = runBlocking {
        csv.reader { file = "unknown" }
            .assertNull()
    }

    @Test
    internal fun `should fail without giving file`(): Unit = runBlocking {
        csv.reader {
            debug = true
            separator = semicolon
        }.assertNull()
    }
}
