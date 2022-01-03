package io.github.kotools.csv.reader

import io.github.kotools.csv.test.assertNotNull
import io.github.kotools.csv.test.assertNull
import org.junit.jupiter.api.Nested
import kotlin.test.Test

class DataTypeTest {
    @Nested
    inner class CreateOrNull {
        @Test
        fun `should pass`(): Unit =
            assertNotNull { DataType createOrNull TypeExample::class }

        @Test
        fun `should fail with a non-data class type`(): Unit =
            assertNull { DataType createOrNull ClassTypeExample::class }

        @Test
        fun `should fail with a private data class type`(): Unit =
            assertNull { DataType createOrNull PrivateTypeExample::class }
    }

    @Nested
    inner class CreateTypeOrNull {
        private val dataType: DataType<TypeExample> by lazy {
            DataType.createOrNull(TypeExample::class)!!
        }

        @Test
        fun `should pass`(): Unit = assertNotNull {
            mapOf("first" to "a", "second" to "1", "third" to "true")
                .let(dataType::createTypeOrNull)
        }

        @Test
        fun `should fail with a record that doesn't match the type`(): Unit =
            assertNull { dataType createTypeOrNull emptyMap() }
    }

    class ClassTypeExample

    data class TypeExample(
        val first: String,
        val second: Int,
        val third: Boolean
    )

    private data class PrivateTypeExample(val a: String)
}
