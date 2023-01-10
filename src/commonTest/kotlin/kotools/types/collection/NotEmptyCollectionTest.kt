package kotools.types.collection

import kotools.types.number.StrictlyPositiveInt
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
}
