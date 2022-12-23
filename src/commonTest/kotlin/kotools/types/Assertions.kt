package kotools.types

import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal fun Throwable.assertHasAMessage(): Unit =
    assertTrue(block = assertNotNull(message)::isNotBlank)
