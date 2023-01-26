package kotools.types.collection

import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.ZeroInt
import kotools.types.shouldEqual
import kotlin.random.Random
import kotlin.test.Test

private typealias NotEmptyCollectionBuilder<E> =
            (Collection<E>) -> NotEmptyCollection<E>

class NotEmptyCollectionTest {
    private val notEmptyCollectionOfRandomInts: NotEmptyCollection<Int>
        get() {
            val collection: Collection<Int> = List(3) { Random.nextInt() }
            val builders: NotEmptyList<NotEmptyCollectionBuilder<Int>> =
                notEmptyListOf(
                    { it.toNotEmptyList().getOrThrow() },
                    { it.toNotEmptySet().getOrThrow() }
                )
            return builders.toList()
                .random()
                .invoke(collection)
        }

    @Test
    fun size_should_return_the_size_of_this_collection_as_a_StrictlyPositiveInt() {
        val collection: NotEmptyCollection<Int> = notEmptyCollectionOfRandomInts
        val result: StrictlyPositiveInt = collection.size
        val expectedCollection: Collection<Int> = when (collection) {
            is NotEmptyList -> collection.toList()
            is NotEmptySet -> collection.toSet()
        }
        result.toInt() shouldEqual expectedCollection.size
    }

    @Test
    fun sizeOrZero_should_pass_with_a_collection_other_than_null() {
        val collection: NotEmptyCollection<Int> = notEmptyCollectionOfRandomInts
        val result: PositiveInt = collection.sizeOrZero
        result.toInt() shouldEqual collection.size.toInt()
    }

    @Test
    fun sizeOrZero_should_fail_with_a_collection_that_equals_null() {
        val collection: NotEmptyCollection<Int>? = null
        val result: PositiveInt = collection.sizeOrZero
        result shouldEqual ZeroInt
    }
}
