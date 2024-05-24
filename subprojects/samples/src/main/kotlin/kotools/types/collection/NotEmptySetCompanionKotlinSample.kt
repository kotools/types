package kotools.types.collection

import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal class NotEmptySetCompanionKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun createWithCollection() {
        val collection: Collection<Int> = setOf(1, 2, 3)
        val elements: NotEmptySet<Int> = NotEmptySet.create(collection)
        println(elements) // [1, 2, 3]
    } // END

    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun createWithMutableCollection() {
        val original: MutableCollection<Int> = mutableSetOf(1, 2, 3)
        val integers: NotEmptySet<Int> = NotEmptySet.create(original)
        println(original) // [1, 2, 3]
        println(integers) // [1, 2, 3]

        original.clear()
        println(original) // []
        println(integers) // [1, 2, 3]
    } // END

    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun createOrNullWithCollection() {
        val collection: Collection<Int> = setOf(1, 2, 3)
        val elements: NotEmptySet<Int>? = NotEmptySet.createOrNull(collection)
        println(elements) // [1, 2, 3]
    } // END

    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun createOrNullWithMutableCollection() {
        val original: MutableCollection<Int> = mutableSetOf(1, 2, 3)
        val integers: NotEmptySet<Int>? = NotEmptySet.createOrNull(original)
        println(original) // [1, 2, 3]
        println(integers) // [1, 2, 3]

        original.clear()
        println(original) // []
        println(integers) // [1, 2, 3]
    } // END

    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun of() {
        val integers: NotEmptySet<Int> = NotEmptySet.of(1, 2, 3, 1)
        println(integers) // [1, 2, 3]
    } // END
}
