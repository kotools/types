import kotlin.test.Test
import kotlin.test.assertEquals

class HelloTest {
    @Test
    fun greetShouldPassWithNoArgument() {
        val actual: String = greet()
        val expected = "Hello World!"
        assertEquals(expected, actual)
    }

    @Test
    fun greetShouldPassWithSpecifiedName() {
        val name = "Test"
        val actual: String = greet(name)
        val expected = "Hello $name!"
        assertEquals(expected, actual)
    }
}
