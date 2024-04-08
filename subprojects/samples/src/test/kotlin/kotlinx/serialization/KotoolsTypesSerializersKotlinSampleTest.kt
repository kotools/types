package org.kotools.types.kotlinx.serialization

import com.github.stefanbirkner.systemlambda.SystemLambda
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.assertPrints
import kotlin.reflect.KClass
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertNotNull

class KotoolsTypesSerializersKotlinSampleTest {
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
        val type: KClass<KotoolsTypesSerializers> =
            KotoolsTypesSerializers::class
        val expected: String = assertNotNull(
            actual = type.simpleName,
            message = "Getting simple name of '$type' shouldn't return null."
        )
        assertPrints(
            expected,
            KotoolsTypesSerializersKotlinSample::toString_override
        )
    }
}
