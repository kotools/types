package org.kotools.types.internal

/** Creates an instance of [PlatformInteger] from the specified [number]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(number: Long): PlatformInteger {
    val integer = BigInt("$number")
    return JsInteger(integer)
}

/**
 * Creates an instance of [PlatformInteger] from the specified [text], or throws
 * an [IllegalArgumentException] if the [text] doesn't represent an integer.
 *
 * The [text] parameter must only contain an optional plus (`+`) or minus
 * (`-`) sign, followed by a sequence of digits.
 */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(text: String): PlatformInteger {
    PlatformInteger.requirements(text)
    val integer = BigInt(text)
    return JsInteger(integer)
}

@OptIn(InternalKotoolsTypesApi::class)
private class JsInteger(private val integer: BigInt) : PlatformInteger {
    override fun minus(other: PlatformInteger): PlatformInteger {
        val difference: BigInt = this.integer - BigInt("$other")
        return JsInteger(difference)
    }

    override fun times(other: PlatformInteger): PlatformInteger {
        val product: BigInt = this.integer * BigInt("$other")
        return JsInteger(product)
    }

    override fun equals(other: Any?): Boolean =
        other is JsInteger && this.integer == other.integer

    override fun hashCode(): Int = this.integer.hashCode()

    override fun toString(): String = this.integer.toString()
}
