package kotools.types.collection

import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal class NotEmptyListCompanionKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun createWithCollection() {
        val collection: Collection<Int> = listOf(1, 2, 3)
        val elements: NotEmptyList<Int> = NotEmptyList.create(collection)
        println(elements) // [1, 2, 3]
    } // END

    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun createWithMutableCollection() {
        val original: MutableCollection<Int> = mutableListOf(1, 2, 3)
        val notEmptyList: NotEmptyList<Int> = NotEmptyList.create(original)
        println(original) // [1, 2, 3]
        println(notEmptyList) // [1, 2, 3]

        original.clear()
        println(original) // []
        println(notEmptyList) // [1, 2, 3]
    } // END

    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun createOrNullWithCollection() {
        val collection: Collection<Int> = listOf(1, 2, 3)
        val elements: NotEmptyList<Int>? = NotEmptyList.createOrNull(collection)
        println(elements) // [1, 2, 3]
    } // END

    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun createOrNullWithMutableCollection() {
        val original: MutableCollection<Int> = mutableListOf(1, 2, 3)
        val notEmptyList: NotEmptyList<Int>? =
            NotEmptyList.createOrNull(original)
        println(original) // [1, 2, 3]
        println(notEmptyList) // [1, 2, 3]

        original.clear()
        println(original) // []
        println(notEmptyList) // [1, 2, 3]
    } // END

    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun of() {
        val integers: NotEmptyList<Int> = NotEmptyList.of(1, 2, 3)
        println(integers) // [1, 2, 3]
    } // END
}
