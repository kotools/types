package com.github.kotools.csvfile.api

import com.github.kotools.csvfile.core.semicolon
import com.github.kotools.csvfile.utils.assertEquals
import com.github.kotools.csvfile.utils.assertNotNullOrEquals
import com.github.kotools.csvfile.utils.assertNull
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class ReaderTest {
    @Test
    fun `should pass asynchronously`(): Unit = runBlocking {
        csvReaderAsync { file = "test" }.await()?.size assertNotNullOrEquals 0
    }

    @Test
    fun `should pass with empty file`(): Unit = runBlocking {
        csvReader { file = "empty.csv" }?.size assertEquals 0
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
    fun `should pass without file's extension`(): Unit = runBlocking {
        csvReader { file = "test" }?.size assertNotNullOrEquals 0
    }

    @Test
    fun `should fail with nonexistent file`(): Unit = runBlocking {
        csvReader { file = "unknown" }.assertNull()
    }

    @Test
    fun `should fail with nonexistent resource file`(): Unit = runBlocking {
        csvReader {
            file = "empty"
            resource = false
        }.assertNull()
    }

    @Test
    fun `should fail without giving file`(): Unit = runBlocking {
        csvReader { }.assertNull()
    }
}
