package kotools.types

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.test.Test

class NegativeIntTest {
    // ---------- NegativeInt.Companion.of(Int) ----------

    @Test
    fun of_should_pass_with_a_negative_Int() {
        val value: Int = NegativeInt.random().toInt()
        NegativeInt.of(value)
            .getOrThrow()
            .toInt() assertEquals value
    }

    @Test
    fun of_should_fail_with_a_strictly_positive_Int() {
        val result: Result<NegativeInt> = StrictlyPositiveInt.random()
            .toInt()
            .let(NegativeInt.Companion::of)
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    // ---------- NegativeInt.unaryMinus() ----------

    @Test
    fun unaryMinus_should_pass() {
        val x: NegativeInt = NegativeInt.random()
        val result: PositiveInt = -x
        result.toInt() assertEquals -x.toInt()
    }
}

class NegativeIntSerializationTest {
    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: NegativeInt = NegativeInt.random()
        val result: String = Json.encodeToString(x)
        result assertEquals Json.encodeToString(x.toInt())
    }

    @Test
    fun deserialization_should_pass() {
        val x: NegativeInt = NegativeInt.random()
        val encoded: String = Json.encodeToString(x)
        val result: NegativeInt = Json.decodeFromString(encoded)
        result.toInt() assertEquals x.toInt()
    }
}
