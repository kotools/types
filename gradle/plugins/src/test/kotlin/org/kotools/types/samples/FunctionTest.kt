package org.kotools.types.samples

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class FunctionTest {
    @Test
    fun `constructor should pass with valid arguments`() {
        val name = "printHelloWorld"
        val body: List<String> = listOf(
            "val message = \"hello world\"",
            "println(message) // hello world"
        )
        Function(name, body)
    }

    @Test
    fun `constructor should fail with blank name`() {
        val name = "  "
        val body: List<String> = listOf(
            "val message = \"hello world\"",
            "println(message) // hello world"
        )
        val exception: IllegalArgumentException = assertFailsWith {
            Function(name, body)
        }
        val actual: String? = exception.message
        val expected: String = Function.BLANK_NAME_ERROR_MESSAGE
        assertEquals(expected, actual)
    }

    @Test
    fun `name should pass`() {
        val name = "printHelloWorld"
        val body: List<String> = listOf(
            "val message = \"hello world\"",
            "println(message) // hello world"
        )
        val actual: String = Function(name, body).name
        assertEquals(expected = name, actual)
    }

    @Test
    fun `bodyText should pass`() {
        val name = "printHelloWorld"
        val body: List<String> = listOf(
            "val message = \"hello world\"",
            "println(message) // hello world"
        )
        val actual: String = Function(name, body).bodyText
        val expected: String = body.joinToString("\n")
        assertEquals(expected, actual)
    }
}
