package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotools.types.number.PositiveInt
import kotools.types.number.positive

// ---------- Builders ----------

inline fun <A, B> A.pairBy(block: (A) -> B): Pair<B, A> = block(this) to this
inline fun <A, B> A.runPairBy(block: A.() -> B): Pair<B, A> = pairBy(block)

inline fun <A, B> A.pairWith(block: (A) -> B): Pair<A, B> = this to block(this)
inline fun <A, B> A.runPairWith(block: A.() -> B): Pair<A, B> = pairWith(block)

// ---------- Assertions ----------

fun <A> Pair<A, A>.assertEquals(): Unit = first assertEquals second

fun <A, B> Pair<A?, B>.assertFirstIsNull(): Unit = first.assertNull()

fun <A, B> Pair<A?, B>.assertFirstIsNotNull(): Pair<A, B> =
    mapFirst { it.assertNotNull() }

// ---------- Mappers ----------

inline fun <A, B, C, D> Pair<A, B>.map(
    first: (Pair<A, B>) -> C,
    second: (Pair<A, B>) -> D
): Pair<C, D> = first(this) to second(this)

inline fun <A, B, C, D> Pair<A, B>.runMap(
    first: Pair<A, B>.() -> C,
    second: Pair<A, B>.() -> D
): Pair<C, D> = map(first, second)

inline fun <A, B, C> Pair<A, B>.mapFirst(block: (A) -> C): Pair<C, B> =
    block(first) to second

inline fun <A, B, C> Pair<A, B>.runMapFirst(block: A.() -> C): Pair<C, B> =
    mapFirst(block)

inline fun <A, B, C> Pair<A, B>.mapSecond(block: (B) -> C): Pair<A, C> =
    first to block(second)

inline fun <A, B, C> Pair<A, B>.runMapSecond(block: B.() -> C): Pair<A, C> =
    mapSecond(block)

// ---------- Reducers ----------

fun <A, B> Pair<Collection<A>, Collection<B>>.toPairs(): List<Pair<A?, B?>> {
    val firstCollection: Collection<A?> = first
        .takeIf { it.size >= second.size }
        ?: mutableListOf<A?>().apply {
            addAll(first)
            repeat(second.size - first.size) { add(null) }
        }
    val secondCollection: Collection<B?> = second
        .takeIf { it.size >= firstCollection.size }
        ?: mutableListOf<B?>().apply {
            addAll(second)
            repeat(firstCollection.size - second.size) { add(null) }
        }
    return firstCollection.mapIndexed { index: Int, element: A? ->
        element to secondCollection.elementAt(index)
    }
}

// ---------- Iterations ----------

inline fun <A, B, C : Collection<B>> Pair<A, C>.forEachSecondIndexed(
    action: (PositiveInt, B, A) -> Unit
): Unit = second.forEachIndexed { index: Int, element: B ->
    action(positive int index, element, first)
}
