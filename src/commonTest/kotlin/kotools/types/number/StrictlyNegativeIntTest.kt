package kotools.types.number

import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotools.assert.assertTrue
import kotools.types.*
import kotlin.test.Test

@Suppress("TestFunctionName")
class StrictlyNegativeIntTest {
    // ---------- Builders ----------

    @Test
    fun StrictlyNegativeInt_should_pass_with_a_strictly_negative_Int(): Unit =
        randomStrictlyNegativeInt()
            .value
            .pairBy(::StrictlyNegativeInt)
            .mapFirst(StrictlyNegativeInt::value)
            .assertEquals()

    @Test
    fun StrictlyNegativeInt_should_throw_an_error_with_a_positive_Int(): Unit =
        randomPositiveInt()
            .runCatching { StrictlyNegativeInt(value) }
            .exceptionOrNull()
            .assertNotNull()
            .apply { message.assertNotNull() }
            .let { it is StrictlyNegativeInt.ConstructionError }
            .assertTrue()

    @Test
    fun StrictlyNegativeIntOrNull_should_pass_with_a_strictly_negative_Int(): Unit =
        randomStrictlyNegativeInt()
            .value
            .pairBy(::StrictlyNegativeIntOrNull)
            .assertFirstIsNotNull()
            .mapFirst(StrictlyNegativeInt::value)
            .assertEquals()

    @Test
    fun StrictlyNegativeIntOrNull_should_return_null_with_a_positive_Int(): Unit =
        randomPositiveInt()
            .value
            .let(::StrictlyNegativeIntOrNull)
            .assertNull()

    // ---------- Unary operations ----------

    @Test
    fun unaryMinus_should_pass(): Unit = randomStrictlyNegativeInt()
        .pairBy { -it }
        .runMap({ first.value }) { -second.value }
        .assertEquals()

    // ---------- Conversions ----------

    @Test
    fun toString_should_pass(): Unit = randomStrictlyNegativeInt()
        .pairBy(StrictlyNegativeInt::toString)
        .runMapSecond { value.toString() }
        .assertEquals()
}

@Suppress("unused")
class StrictlyNegativeIntSerializerTest :
    IntHolderSerializerTest<StrictlyNegativeInt>(
        StrictlyNegativeIntSerializer,
        ::randomStrictlyNegativeInt
    )
