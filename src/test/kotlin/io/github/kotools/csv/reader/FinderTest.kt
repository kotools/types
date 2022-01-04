package io.github.kotools.csv.reader

import io.github.kotools.csv.assertNotNull
import io.github.kotools.csv.assertNull
import kotlin.test.Test

class FinderTest {
    @Test
    fun `findOrNull should pass`(): Unit = assertNotNull {
        ReaderConfiguration.createOrNull {
            file = "test"
            folder = "folder"
        }?.let(::Finder)
            ?.findOrNull()
    }

    @Test
    fun `findOrNull should fail`(): Unit = assertNull {
        ReaderConfiguration.createOrNull { file = "unknown" }!!
            .let(::Finder)
            .findOrNull()
    }
}
