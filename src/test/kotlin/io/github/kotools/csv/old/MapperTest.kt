package io.github.kotools.csv.old

import io.github.kotools.csv.assertEquals
import io.github.kotools.csv.assertNotNull
import io.github.kotools.csv.assertNull
import org.junit.jupiter.api.Nested
import kotlin.test.Test

class MapperTest {
    private val example: Example by lazy {
        Example(first = "a", second = 2, third = false)
    }
    private val map: Map<String, String> by lazy {
        mapOf(
            "first" to example.first,
            "second" to example.second.toString(),
            "third" to example.third.toString()
        )
    }

    @Nested
    inner class ToTypeOrNull {
        @Test
        fun `should pass`() {
            (map toTypeOrNull Example::class).run {
                assertNotNull()
                this?.assertEquals(example)
            }
        }

        @Test
        fun `should fail with a non-data class type`(): Unit =
            (map toTypeOrNull this::class).assertNull()

        @Test
        fun `should fail with a type that doesn't match the map`(): Unit =
            (map toTypeOrNull Example2::class).assertNull()
    }
}

data class Example(
    val first: String,
    val second: Int,
    val third: Boolean
)

data class Example2(val first2: String)
