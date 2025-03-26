package kotools.types.collection

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NotEmptyListCompanionCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun of() {
        val integers: NotEmptyList<Int> = NotEmptyList.of(1, 2, 3)
        assertEquals(expected = "[1, 2, 3]", actual = "$integers")
    }
}
