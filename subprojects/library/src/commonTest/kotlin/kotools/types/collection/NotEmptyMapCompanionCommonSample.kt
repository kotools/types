package kotools.types.collection

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NotEmptyMapCompanionCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun of() {
        val map: NotEmptyMap<Char, Int> = NotEmptyMap.of('a' to 1, 'b' to 2)
        assertEquals(expected = "{a=1, b=2}", actual = "$map")
    }
}
