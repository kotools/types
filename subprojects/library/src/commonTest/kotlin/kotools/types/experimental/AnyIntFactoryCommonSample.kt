package kotools.types.experimental

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

internal class AnyIntFactoryCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun constructorLikeInt() {
        val number = AnyInt(1)
        assertEquals(expected = "1", actual = "$number")
    }
}
