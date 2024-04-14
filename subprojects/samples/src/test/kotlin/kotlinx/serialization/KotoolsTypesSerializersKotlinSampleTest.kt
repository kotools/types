package org.kotools.types.kotlinx.serialization

import com.github.stefanbirkner.systemlambda.SystemLambda
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.simpleNameOf
import org.kotools.types.assertPrints
import kotlin.test.Test
import kotlin.test.assertContentEquals

class KotoolsTypesSerializersKotlinSampleTest {
    @Test
    fun `all should pass`() {
        val actual: List<String> = SystemLambda
            .tapSystemOut(KotoolsTypesSerializersKotlinSample::all)
            .trim()
            .lines()
        val expected: List<String> = listOf("0", "true")
        assertContentEquals(expected, actual)
    }

    @Test
    fun `zero should pass`() {
        val actual: List<String> = SystemLambda
            .tapSystemOut(KotoolsTypesSerializersKotlinSample::zero)
            .trim()
            .lines()
        val expected: List<String> = listOf("0", "true")
        assertContentEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun `toString() should pass`() {
        val expected: String = simpleNameOf<KotoolsTypesSerializers>()
        assertPrints(
            expected,
            KotoolsTypesSerializersKotlinSample::toString_override
        )
    }
}
