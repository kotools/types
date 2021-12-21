package io.github.kotools.csv

import io.github.kotools.csv.api.semicolon
import io.github.kotools.csv.utils.assertNotNullOrEquals
import io.github.kotools.csv.utils.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import kotlin.test.Test

class ReaderTest {
    @Nested
    inner class Regular {
        @Test
        fun `should pass`(): Unit = runBlocking {
            csvReaderAsync {
                file = "test"
                folder = "folder"
                separator = semicolon
            }.await()?.size assertNotNullOrEquals 0
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
}