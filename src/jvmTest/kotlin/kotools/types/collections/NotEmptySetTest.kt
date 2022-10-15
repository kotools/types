package kotools.types.collections

import kotools.assert.*
import kotools.types.core.RandomValueHolder
import kotools.types.pairBy
import kotools.types.runMapFirst
import kotools.types.runPairBy

class NotEmptySetTest : RandomValueHolder {
    // ---------- Builders ----------

    @Test
    fun constructor_should_pass(): Unit = setOf(randomInt, randomInt, randomInt)
        .runPairBy { first() to toList().subList(1, size).toTypedArray() }
        .runMapFirst { NotEmptySet(first, *second) }
        .run {
            first.size assertEquals second.size
            first.forEachIndexed { index: Int, element: Int ->
                element assertEquals second.elementAt(index)
            }
        }

    @Test
    fun collection_toNotEmptySet_should_pass_with_a_not_empty_collection(): Unit =
        setOf(randomInt, randomInt, randomInt)
            .pairBy(Set<Int>::toNotEmptySet)
            .run {
                first.size assertEquals second.size
                first.forEachIndexed { index: Int, element: Int ->
                    element assertEquals second.elementAt(index)
                }
            }

    @Test
    fun collection_toNotEmptySet_should_throw_an_error_with_an_empty_collection(): Unit =
        emptySet<Int>()
            .runCatching { toNotEmptySet() }
            .exceptionOrNull()
            .assertNotNull()
            .apply { message.assertNotNull() }
            .let { it is IllegalArgumentException }
            .assertTrue()

    @Test
    fun array_toNotEmptySet_should_pass(): Unit =
        arrayOf(randomInt, randomInt, randomInt)
            .pairBy(Array<Int>::toNotEmptySet)
            .run {
                first.size assertEquals second.size
                first.forEachIndexed { index: Int, element: Int ->
                    element assertEquals second.elementAt(index)
                }
            }

    @Test
    fun array_toNotEmptySet_should_throw_an_error_with_an_empty_array(): Unit =
        emptyArray<Int>()
            .runCatching { toNotEmptySet() }
            .exceptionOrNull()
            .assertNotNull()
            .apply { message.assertNotNull() }
            .let { it is IllegalArgumentException }
            .assertTrue()

    @Test
    fun collection_toNotEmptySetOrElse_should_pass_with_a_not_empty_collection(): Unit =
        (setOf(randomInt, randomInt) to NotEmptySet(randomInt))
            .runPairBy { first.toNotEmptySetOrElse { second } }
            .run {
                first.size assertEquals second.first.size
                first.forEachIndexed { index: Int, element: Int ->
                    element assertEquals second.first.elementAt(index)
                }
                first.size assertNotEquals second.second.size
            }

    @Test
    fun collection_toNotEmptySetOrElse_should_return_the_default_value_with_an_empty_collection(): Unit =
        (emptySet<Int>() to NotEmptySet(randomInt, randomInt))
            .runPairBy { first.toNotEmptySetOrElse { second } }
            .run {
                first.size assertNotEquals second.first.size
                first.size assertEquals second.second.size
                first.forEachIndexed { index: Int, element: Int ->
                    element assertEquals second.second.elementAt(index)
                }
            }

    @Test
    fun array_toNotEmptySetOrElse_should_pass_with_a_not_empty_array(): Unit =
        (arrayOf(randomInt, randomInt) to NotEmptySet(randomInt))
            .runPairBy { first.toNotEmptySetOrElse { second } }
            .run {
                first.size assertEquals second.first.size
                first.forEachIndexed { index: Int, element: Int ->
                    element assertEquals second.first.elementAt(index)
                }
                first.size assertNotEquals second.second.size
            }

    @Test
    fun array_toNotEmptySetOrElse_should_return_the_default_value_with_an_empty_array(): Unit =
        (emptyArray<Int>() to NotEmptySet(randomInt, randomInt))
            .runPairBy { first.toNotEmptySetOrElse { second } }
            .run {
                first.size assertNotEquals second.first.size
                first.size assertEquals second.second.size
                first.forEachIndexed { index: Int, element: Int ->
                    element assertEquals second.second.elementAt(index)
                }
            }
}
