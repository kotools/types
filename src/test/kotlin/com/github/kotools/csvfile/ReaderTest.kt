package com.github.kotools.csvfile

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

@ExperimentalCoroutinesApi
class ReaderTest {
    @Test
    fun `should pass with empty file`(): Unit = runBlocking {
        (csv reader { file = "empty.csv" })?.size assertEquals 0
    }

    @Test
    fun `should pass without file's extension`(): Unit = runBlocking {
        (csv reader { file = "test" })?.size assertNotEquals 0
    }

    @Test
    fun `should fail with nonexistent file`(): Unit = runBlocking {
        assertNull {
            csv reader { file = "unknown" }
        }
    }

    @Test
    fun `should fail without configuration`(): Unit = runBlocking {
        assertNull {
            csv reader { }
        }
    }
}
