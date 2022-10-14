package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertNotNull
import kotools.assert.assertNull

// ---------- Builders ----------

inline fun <A, B> A.pairBy(block: (A) -> B): Pair<B, A> = block(this) to this

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

inline fun <A, B, C> Pair<A, B>.mapSecond(block: (B) -> C): Pair<A, C> =
    first to block(second)

inline fun <A, B, C> Pair<A, B>.runMapSecond(block: B.() -> C): Pair<A, C> =
    mapSecond(block)
