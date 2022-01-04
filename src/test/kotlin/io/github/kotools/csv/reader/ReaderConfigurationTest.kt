package io.github.kotools.csv.reader

import io.github.kotools.csv.assertNotNull
import io.github.kotools.csv.assertNull
import kotlin.test.Test

class ReaderConfigurationTest {
    @Test
    fun `creation should pass`(): Unit = assertNotNull {
        ReaderConfiguration createOrNull { file = "test" }
    }

    @Test
    fun `creation should fail`(): Unit = assertNull {
        ReaderConfiguration createOrNull {}
    }
}
