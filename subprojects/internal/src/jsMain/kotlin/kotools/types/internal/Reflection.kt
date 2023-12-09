/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.internal

/** Returns the serial name of a given type [T]. */
public actual inline fun <reified T : Any> serialNameOf(): String =
    T::class.simpleName ?: error("Simple name shouldn't be null")
