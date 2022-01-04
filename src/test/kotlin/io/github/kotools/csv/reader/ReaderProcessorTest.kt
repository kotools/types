package io.github.kotools.csv.reader

import io.github.kotools.csv.test.assertNotNull
import io.github.kotools.csv.test.assertNull
import kotlin.test.Test

class ReaderProcessorTest {
    @Test
    fun `processOrNull should pass`(): Unit = assertNotNull {
        ReaderProcessor(TypeExample::class) {
            file = "test"
            folder = "folder"
        }.processOrNull()
    }

    @Test
    fun `processOrNull should fail with an invalid configuration`(): Unit =
        assertNull {
            ReaderProcessor(TypeExample::class) {}
                .processOrNull()
        }

    @Test
    fun `processOrNull should fail with an invalid type`(): Unit = assertNull {
        ReaderProcessor(ClassTypeExample::class) {
            file = "test"
            folder = "folder"
        }.processOrNull()
    }

    @Test
    fun `processOrNull should fail with a nonexistent file`(): Unit =
        assertNull {
            ReaderProcessor(TypeExample::class) { file = "unknown" }
                .processOrNull()
        }

    class ClassTypeExample

    data class TypeExample(
        val first: String,
        val second: Int,
        val third: Boolean
    )
}
