/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.internal

import kotlin.test.Test
import kotlin.test.assertEquals

class KotoolsTypesVersionTest {
    @Test
    fun toString_should_pass(): Unit = KotoolsTypesVersion.values().forEach {
        val actual = "$it"
        val expected: String = it.name.drop(1)
            .replace('_', '.')
        assertEquals(expected, actual)
    }
}
