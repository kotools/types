package kotools.types.number

import com.github.stefanbirkner.systemlambda.SystemLambda
import kotlin.test.Test
import kotlin.test.assertEquals

class ZeroKotlinSampleTest {
    @Test
    fun toStringSample_should_pass() {
        val actual: String = SystemLambda
            .tapSystemOut(ZeroKotlinSample::toStringSample)
            .trim()
        val expected = "0"
        val message =
            "Kotlin sample of 'Zero.toString()' should print '$expected'."
        assertEquals(expected, actual, message)
    }
}
