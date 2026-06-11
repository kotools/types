package org.kotools.types.internal.number

import org.kotools.types.internal.InternalKotoolsTypesApi
import kotlin.math.absoluteValue

/** Returns a [PlatformInteger] representing the specified [value]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(value: Long): PlatformInteger =
    NativeInteger.fromLong(value)

/** Returns a [PlatformInteger] representing the number described by [value]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(value: String): PlatformInteger =
    NativeInteger.parse(value)

@OptIn(InternalKotoolsTypesApi::class)
private class NativeInteger private constructor(
    private val magnitude: UIntArray,
    private val sign: IntegerSign
) : PlatformInteger {
    init {
        require(magnitude.isEmpty() || magnitude.last() != 0u) {
            "Magnitude must not have a zero most-significant limb."
        }
        require(magnitude.isEmpty() == (sign == IntegerSign.Zero)) {
            "Magnitude must be empty iff sign is Zero."
        }
    }

    // ----------------------- Class-level declarations ------------------------

    companion object {
        // ------------------ Constants and factory functions ------------------

        private val ZERO: NativeInteger = NativeInteger(
            magnitude = UIntArray(size = 0),
            IntegerSign.Zero
        )

        fun fromLong(value: Long): NativeInteger {
            if (value == 0L) return ZERO
            if (value == Long.MIN_VALUE) return NativeInteger(
                // 2^63 = [0, 2^31] in base-2^32
                magnitude = uintArrayOf(0u, Int.MIN_VALUE.toUInt()),
                IntegerSign.Negative
            )

            val sign: IntegerSign =
                if (value > 0L) IntegerSign.Positive
                else IntegerSign.Negative

            val absoluteValue: Long = value.absoluteValue
            val lowBits: UInt = (absoluteValue and 0xFFFFFFFFL).toUInt()
            val highBits: UInt = (absoluteValue ushr 32).toUInt()
            val magnitude: UIntArray =
                if (highBits != 0u) uintArrayOf(lowBits, highBits)
                else uintArrayOf(lowBits)

            return NativeInteger(magnitude, sign)
        }

        fun parse(value: String): NativeInteger {
            val isNegative: Boolean = value.first() == '-'
            val digits: String = if (isNegative) value.substring(1) else value
            if (digits == "0") return ZERO

            var tmpMag = UIntArray(0)
            digits.forEach {
                val x: UIntArray = tmpMag * uintArrayOf(10u)
                val y: UIntArray = uintArrayOf(it.digitToInt().toUInt())
                tmpMag = x + y
            }
            val magnitude: UIntArray = tmpMag.trimLeadingZeros()

            val sign: IntegerSign = when {
                magnitude.isEmpty() -> IntegerSign.Zero
                isNegative -> IntegerSign.Negative
                else -> IntegerSign.Positive
            }

            return NativeInteger(magnitude, sign)
        }
    }

    // ------------------------------ Comparisons ------------------------------

    override fun equals(other: Any?): Boolean = other is NativeInteger
            && this.sign == other.sign
            && this.magnitude.contentEquals(other.magnitude)

    override fun hashCode(): Int =
        31 * sign.hashCode() + magnitude.contentHashCode()

    override fun compareTo(other: PlatformInteger): Int {
        val oth = other as NativeInteger
        if (this.sign != oth.sign) return this.sign.compare(oth.sign)
        if (this.sign == IntegerSign.Zero) return 0
        val comparison: Int = this.magnitude.compareTo(oth.magnitude)
        return if (this.sign == IntegerSign.Positive) comparison
        else -comparison
    }

    // ------------------------- Arithmetic operations -------------------------

    override fun unaryMinus(): PlatformInteger =
        NativeInteger(this.magnitude, -this.sign)

    override fun plus(other: PlatformInteger): PlatformInteger {
        val oth = other as NativeInteger
        if (this.sign == IntegerSign.Zero) return oth
        if (oth.sign == IntegerSign.Zero) return this
        if (this.sign == oth.sign)
            return NativeInteger(this.magnitude + oth.magnitude, this.sign)
        val comparison: Int = this.magnitude.compareTo(oth.magnitude)
        return when {
            comparison == 0 -> ZERO
            comparison > 0 -> NativeInteger(
                this.magnitude - oth.magnitude,
                this.sign
            )
            else -> NativeInteger(oth.magnitude - this.magnitude, oth.sign)
        }
    }

    override fun minus(other: PlatformInteger): PlatformInteger =
        this + (-other)

    override fun times(other: PlatformInteger): PlatformInteger {
        val oth = other as NativeInteger
        if (this.sign == IntegerSign.Zero || oth.sign == IntegerSign.Zero)
            return ZERO
        val magnitude: UIntArray = this.magnitude * oth.magnitude
        val sign: IntegerSign = this.sign * oth.sign
        return NativeInteger(magnitude, sign)
    }

    override fun div(other: PlatformInteger): PlatformInteger =
        this.divModMagnitudes(other as NativeInteger).first

    override fun rem(other: PlatformInteger): PlatformInteger =
        this.divModMagnitudes(other as NativeInteger).second

    private fun divModMagnitudes(
        other: NativeInteger
    ): Pair<NativeInteger, NativeInteger> {
        if (this.sign == IntegerSign.Zero) return ZERO to ZERO
        val divisorAbs: NativeInteger = other.abs()
        val (magQuotient, magRemainder) = this.magnitude / divisorAbs.magnitude
        val quotientSign: IntegerSign = this.sign * other.sign
        if (this.sign == IntegerSign.Positive) {
            val quotient: NativeInteger =
                if (magQuotient.isEmpty()) ZERO
                else NativeInteger(magQuotient, quotientSign)
            val remainder: NativeInteger =
                if (magRemainder.isEmpty()) ZERO
                else NativeInteger(magRemainder, IntegerSign.Positive)
            return quotient to remainder
        }
        if (magRemainder.isEmpty())
            return NativeInteger(magQuotient, quotientSign) to ZERO
        val quotient = NativeInteger(
            magQuotient + uintArrayOf(1u),
            quotientSign
        )
        val remainder = NativeInteger(
            divisorAbs.magnitude - magRemainder,
            IntegerSign.Positive
        )
        return quotient to remainder
    }

    private fun abs(): NativeInteger =
        if (this.sign == IntegerSign.Negative)
            NativeInteger(magnitude, IntegerSign.Positive)
        else this

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String {
        if (this.sign == IntegerSign.Zero) return "0"
        val builder = StringBuilder()
        var remaining: UIntArray = this.magnitude
        while (remaining.isNotEmpty()) {
            val (quotient: UIntArray, digit: Int) = remaining.divideByTen()
            builder.append(digit)
            remaining = quotient.trimLeadingZeros()
        }
        if (this.sign == IntegerSign.Negative) builder.append('-')
        return builder.toString()
            .reversed()
    }
}

// ----------------------------- Magnitude helpers -----------------------------

private fun UIntArray.trimLeadingZeros(): UIntArray {
    var newSize: Int = this.size
    while (newSize > 0 && this[newSize - 1] == 0u) newSize--
    return if (newSize == this.size) this
    else copyOf(newSize)
}

private operator fun UIntArray.compareTo(other: UIntArray): Int {
    if (this.size != other.size) return this.size.compareTo(other.size)
    val index: Int = this.indices.reversed()
        .firstOrNull { this[it] != other[it] }
        ?: return 0
    return this[index].compareTo(other[index])
}

private operator fun UIntArray.plus(other: UIntArray): UIntArray {
    val maxSize: Int = maxOf(this.size, other.size)
    val result = UIntArray(maxSize + 1)
    var carry = 0L
    (0 until maxSize).forEach {
        val x: Long = if (it < this.size) this[it].toLong() else 0L
        val y: Long = if (it < other.size) other[it].toLong() else 0L
        val sum: Long = x + y + carry
        result[it] = sum.toUInt()
        carry = sum ushr 32
    }
    result[maxSize] = carry.toUInt()
    return result.trimLeadingZeros()
}

private operator fun UIntArray.minus(other: UIntArray): UIntArray {
    val result = UIntArray(this.size)
    var borrow = 0L
    this.indices.forEach {
        val x: Long = this[it].toLong()
        val y: Long = if (it < other.size) other[it].toLong() else 0L
        val difference: Long = x - y - borrow
        result[it] = difference.toUInt()
        borrow = if (difference < 0L) 1L else 0L
    }
    return result.trimLeadingZeros()
}

private operator fun UIntArray.times(other: UIntArray): UIntArray {
    if (this.isEmpty() || other.isEmpty()) return UIntArray(0)
    val result = UIntArray(this.size + other.size)
    this.indices.forEach { index1: Int ->
        var carry = 0L
        other.indices.forEach { index2: Int ->
            val product: Long = this[index1].toLong() * other[index2].toLong() +
                    result[index1 + index2].toLong() + carry
            result[index1 + index2] = product.toUInt()
            carry = product ushr 32
        }
        result[index1 + other.size] = carry.toUInt()
    }
    return result.trimLeadingZeros()
}

private fun UIntArray.bitLength(): Int =
    if (this.isEmpty()) 0
    else (this.size - 1) * 32 + (32 - this.last().countLeadingZeroBits())

private operator fun UIntArray.div(
    other: UIntArray
): Pair<UIntArray, UIntArray> {
    val comparison: Int = this.compareTo(other)
    if (comparison < 0) return UIntArray(0) to this
    if (comparison == 0) return uintArrayOf(1u) to UIntArray(0)
    val shift: Int = this.bitLength() - other.bitLength()
    val quotientMagnitude = UIntArray(shift / 32 + 1)
    var remainder: UIntArray = this
    var shifted: UIntArray = other.shiftLeft(shift)
    (shift downTo 0).forEach {
        if (remainder >= shifted) {
            remainder -= shifted
            val quotient: Long =
                quotientMagnitude[it / 32].toLong() or (1L shl (it % 32))
            quotientMagnitude[it / 32] = quotient.toUInt()
        }
        if (it > 0) shifted = shifted.shiftRight(1)
    }
    return quotientMagnitude.trimLeadingZeros() to remainder.trimLeadingZeros()
}

private fun UIntArray.shiftLeft(bits: Int): UIntArray {
    if (bits == 0 || this.isEmpty()) return this
    val limbShift: Int = bits / 32
    val bitShift: Int = bits % 32
    val result = UIntArray(this.size + limbShift + 1)
    this.indices.forEach {
        val origin: Long = this[it].toLong()
        val shifted: Long = (origin shl bitShift) and 0xFFFFFFFFL
        val final: Long = result[it + limbShift].toLong() or shifted
        result[it + limbShift] = final.toUInt()
        if (bitShift > 0) {
            val nextShift: Long = origin ushr (32 - bitShift)
            val nextFinal = result[it + limbShift + 1].toLong() or nextShift
            result[it + limbShift + 1] = nextFinal.toUInt()
        }
    }
    return result.trimLeadingZeros()
}

private fun UIntArray.shiftRight(bits: Int): UIntArray {
    if (bits == 0 || this.isEmpty()) return this
    val limbShift: Int = bits / 32
    val bitShift: Int = bits % 32
    if (limbShift >= this.size) return UIntArray(0)
    val result = UIntArray(this.size - limbShift)
    result.indices.forEach {
        val low: Long = this[it + limbShift].toLong() ushr bitShift
        val high: Long =
            if (bitShift > 0 && it + limbShift + 1 < this.size) {
                val original: Long = this[it + limbShift + 1].toLong()
                (original shl (32 - bitShift)) and 0xFFFFFFFFL
            } else 0L
        result[it] = (low or high).toUInt()
    }
    return result.trimLeadingZeros()
}

private fun UIntArray.divideByTen(): Pair<UIntArray, Int> {
    val result = UIntArray(this.size)
    var remainder = 0L
    this.indices.reversed()
        .forEach {
            val current: Long = (remainder shl 32) or this[it].toLong()
            result[it] = (current / 10L).toUInt()
            remainder = current % 10L
        }
    return result.trimLeadingZeros() to remainder.toInt()
}
