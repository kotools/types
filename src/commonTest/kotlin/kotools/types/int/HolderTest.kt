package kotools.types.int

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.types.core.Randomizer
import kotlin.test.Test

class IntHolderTest : Randomizer {
    // ---------- Binary operations ----------

    @Test
    fun plus_should_pass_with_an_Int() {
        // GIVEN
        val xValue: Int = randomInt
        val x = IntHolder(xValue)
        val y: Int = randomInt
        // WHEN
        val result: Int = x + y
        // THEN
        result assertEquals xValue + y
    }

    @Test
    fun plus_should_pass_with_an_IntHolder() {
        // GIVEN
        val xValue: Int = randomInt
        val yValue: Int = randomInt
        val x = IntHolder(xValue)
        val y = IntHolder(yValue)
        // WHEN
        val result: Int = x + y
        // THEN
        result assertEquals xValue + yValue
    }

    @Test
    fun plus_should_pass_when_adding_an_IntHolder_to_an_Int() {
        // GIVEN
        val x: Int = randomInt
        val yValue: Int = randomInt
        val y = IntHolder(yValue)
        // WHEN
        val result: Int = x + y
        // THEN
        result assertEquals x + yValue
    }

    @Test
    fun minus_should_pass_with_an_Int() {
        // GIVEN
        val xValue: Int = randomInt
        val x = IntHolder(xValue)
        val y: Int = randomInt
        // WHEN
        val result: Int = x - y
        // THEN
        result assertEquals xValue - y
    }

    @Test
    fun minus_should_pass_with_an_IntHolder() {
        // GIVEN
        val xValue: Int = randomInt
        val yValue: Int = randomInt
        val x = IntHolder(xValue)
        val y = IntHolder(yValue)
        // WHEN
        val result: Int = x - y
        // THEN
        result assertEquals xValue - yValue
    }

    @Test
    fun minus_should_pass_when_subtracting_an_IntHolder_from_an_Int() {
        // GIVEN
        val x: Int = randomInt
        val yValue: Int = randomInt
        val y = IntHolder(yValue)
        // WHEN
        val result: Int = x - y
        // THEN
        result assertEquals x - yValue
    }

    @Test
    fun times_should_pass_with_an_Int() {
        // GIVEN
        val xValue: Int = randomInt
        val x = IntHolder(xValue)
        val y: Int = randomInt
        // WHEN
        val result: Int = x * y
        // THEN
        result assertEquals xValue * y
    }

    @Test
    fun times_should_pass_with_an_IntHolder() {
        // GIVEN
        val xValue: Int = randomInt
        val yValue: Int = randomInt
        val x = IntHolder(xValue)
        val y = IntHolder(yValue)
        // WHEN
        val result: Int = x * y
        // THEN
        result assertEquals xValue * yValue
    }

    @Test
    fun times_should_pass_when_multiplying_an_Int_with_an_IntHolder() {
        // GIVEN
        val x: Int = randomInt
        val yValue: Int = randomInt
        val y = IntHolder(yValue)
        // WHEN
        val result: Int = x * y
        // THEN
        result assertEquals x * yValue
    }

    @Test
    fun div_should_pass_with_an_Int() {
        // GIVEN
        val xValue: Int = randomInt
        val x = IntHolder(xValue)
        val y: Int = randomInt
        // WHEN
        val result: Int = x / y
        // THEN
        result assertEquals xValue / y
    }

    @Test
    fun div_should_pass_with_an_IntHolder() {
        // GIVEN
        val xValue: Int = randomInt
        val yValue: Int = randomInt
        val x = IntHolder(xValue)
        val y = IntHolder(yValue)
        // WHEN
        val result: Int = x / y
        // THEN
        result assertEquals xValue / yValue
    }

    @Test
    fun div_should_pass_when_dividing_an_Int_by_an_IntHolder() {
        // GIVEN
        val x: Int = randomInt
        val yValue: Int = randomInt
        val y = IntHolder(yValue)
        // WHEN
        val result: Int = x / y
        // THEN
        result assertEquals x / yValue
    }

    // ---------- Comparisons ----------

    @Test
    fun compareTo_should_pass_with_an_Int() {
        // GIVEN
        val xValue: Int = randomInt
        val x = IntHolder(xValue)
        val y: Int = randomInt
        // WHEN
        val result: Int = x compareTo y
        // THEN
        result assertEquals xValue.compareTo(y)
    }

    @Test
    fun compareTo_should_pass_with_an_IntHolder() {
        // GIVEN
        val xValue: Int = randomInt
        val yValue: Int = randomInt
        val x = IntHolder(xValue)
        val y = IntHolder(yValue)
        // WHEN
        val result: Int = x compareTo y
        // THEN
        result assertEquals xValue.compareTo(yValue)
    }

    @Test
    fun compareTo_should_pass_when_comparing_an_Int_with_an_IntHolder() {
        // GIVEN
        val x: Int = randomInt
        val yValue: Int = randomInt
        val y = IntHolder(yValue)
        // WHEN
        val result: Int = x compareTo y
        // THEN
        result assertEquals x.compareTo(yValue)
    }
}

class IntHolderSerializerTest : Randomizer {
    private val serializer: IntHolderSerializer<IntHolder> by lazy {
        IntHolderSerializer(builder = ::IntHolder)
    }

    @Test
    fun serialize_should_pass() {
        // GIVEN
        val value: Int = randomInt
        val x = IntHolder(value)
        // WHEN
        val result: String = Json.encodeToString(serializer, x)
        // THEN
        result assertEquals Json.encodeToString(value)
    }

    @Test
    fun deserialize_should_pass() {
        // GIVEN
        val value: Int = randomInt
        val encoded: String = Json.encodeToString(value)
        // WHEN
        val result: IntHolder = Json.decodeFromString(serializer, encoded)
        // THEN
        result.value assertEquals value
    }
}
