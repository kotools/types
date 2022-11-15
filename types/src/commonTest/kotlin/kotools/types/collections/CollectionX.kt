package kotools.types.collections

inline fun <A, B> Collection<A>.zip(
    other: (Collection<A>) -> Collection<B>
): List<Pair<A, B>> = zip(other(this))
