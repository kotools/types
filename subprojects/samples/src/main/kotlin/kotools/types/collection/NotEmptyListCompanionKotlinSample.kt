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
}
