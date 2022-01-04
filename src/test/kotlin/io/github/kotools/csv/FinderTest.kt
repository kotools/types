package io.github.kotools.csv

import kotlin.test.Test

class FinderTest {
    @Test
    fun `findOrNull should pass`(): Unit = assertNotNull {
        findOrNull {
            ReaderImplementation.createOrNull(TypeExample::class) {
                file = "test"
                folder = "folder"
            }!!
        }
    }

    @Test
    fun `findOrNull should fail`(): Unit = assertNull {
        findOrNull {
            ReaderImplementation.createOrNull(TypeExample::class) {
                file = "unknown"
            }!!
        }
    }

    data class TypeExample(
        val first: String,
        val second: Int,
        val third: Boolean
    )
}
