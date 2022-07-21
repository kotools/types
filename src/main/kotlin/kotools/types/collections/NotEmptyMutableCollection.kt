package kotools.types.collections

import kotools.types.annotations.SinceKotoolsTypes

/**
 * Represents mutable collections containing at least one element.
 *
 * @param E The type of elements contained in this collection.
 */
@SinceKotoolsTypes("1.3")
internal sealed interface NotEmptyMutableCollection<E> : MutableCollection<E>,
    NotEmptyCollection<E>
