package kotools.types.collection

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NotEmptySetCompanionCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun of() {
        val integers: NotEmptySet<Int> = NotEmptySet.of(1, 2, 3, 1)
        assertEquals(expected = "[1, 2, 3]", actual = "$integers")
    }
}
