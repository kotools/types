package kotools.types.collections

import kotools.types.annotations.SinceKotoolsTypes

// TODO: 26/07/2022 Remove this type
/**
 * Represents mutable collections containing at least one element.
 *
 * @param E The type of elements contained in this collection.
 */
@SinceKotoolsTypes("1.3")
public sealed interface NotEmptyMutableCollection<E> : MutableCollection<E>,
    NotEmptyCollection<E>
