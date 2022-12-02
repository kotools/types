package kotools.types

import kotools.assert.assertEquals
import kotlin.test.Test

class ExplicitIntTest {
    // ---------- ExplicitInt.toNotBlankString() ----------

    @Test
    fun toNotBlankString_should_pass() {
        val x: ExplicitInt = NonZeroInt.random()
        x.toNotBlankString().toString() assertEquals x.toInt().toString()
    }
}
