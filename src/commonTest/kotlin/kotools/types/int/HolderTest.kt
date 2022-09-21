package kotools.types.int

import kotools.assert.assertEquals
import kotlin.random.Random
import kotlin.test.Test

class IntHolderTest {
    // ---------- Binary operations ----------

    @Test
    fun plus_should_pass_with_an_Int() {
        val xValue: Int = Random.nextInt()
        val x = IntHolder(xValue)
        val y: Int = Random.nextInt()
        val result: Int = x + y
        result assertEquals xValue + y
    }

    @Test
    fun plus_should_pass_with_an_IntHolder() {
        val xValue: Int = Random.nextInt()
        val yValue: Int = Random.nextInt()
        val x = IntHolder(xValue)
        val y = IntHolder(yValue)
        val result: Int = x + y
        result assertEquals xValue + yValue
    }

    @Test
    fun plus_should_pass_when_adding_an_IntHolder_to_an_Int() {
        val x: Int = Random.nextInt()
        val yValue: Int = Random.nextInt()
        val y = IntHolder(yValue)
        val result: Int = x + y
        result assertEquals x + yValue
    }

    // ---------- Comparisons ----------

    @Test
    fun compareTo_should_pass_with_an_Int() {
        val xValue: Int = Random.nextInt()
        val x = IntHolder(xValue)
        val y: Int = Random.nextInt()
        val result: Int = x compareTo y
        result assertEquals xValue.compareTo(y)
    }

    @Test
    fun compareTo_should_pass_with_an_IntHolder() {
        val xValue: Int = Random.nextInt()
        val yValue: Int = Random.nextInt()
        val x = IntHolder(xValue)
        val y = IntHolder(yValue)
        val result: Int = x compareTo y
        result assertEquals xValue.compareTo(yValue)
    }

    @Test
    fun compareTo_should_pass_when_comparing_an_Int_with_an_IntHolder() {
        val x: Int = Random.nextInt()
        val yValue: Int = Random.nextInt()
        val y = IntHolder(yValue)
        val result: Int = x compareTo y
        result assertEquals x.compareTo(yValue)
    }
}
