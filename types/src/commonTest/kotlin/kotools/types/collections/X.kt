package kotools.types.collections

inline fun <A, B> Collection<A>.zip(
    other: (Collection<A>) -> Collection<B>
): List<Pair<A, B>> = zip(other(this))

inline fun <A, B> Array<A>.zip(
    other: (Array<A>) -> Collection<B>
): List<Pair<A, B>> = zip(other(this))
