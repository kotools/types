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
            var mag = UIntArray(0)
            for (ch in digits) {
                mag = addMagnitudes(
                    multiplyMagnitudes(mag, uintArrayOf(10u)),
                    uintArrayOf(ch.digitToInt().toUInt())
                )
            }
            val trimmed: UIntArray = trimLeadingZeros(mag)
            val sign: IntegerSign = when {
                trimmed.isEmpty() -> IntegerSign.Zero
                isNegative -> IntegerSign.Negative
                else -> IntegerSign.Positive
            }
            return NativeInteger(trimmed, sign)
        }

        // ------------------------- Magnitude helpers -------------------------

        private fun divTruncated(
            dividend: NativeInteger,
            divisor: NativeInteger
        ): NativeInteger {
            if (divisor.sign == IntegerSign.Zero)
                throw ArithmeticException("Division by zero")
            if (dividend.sign == IntegerSign.Zero) return ZERO
            val cmp = compareMagnitudes(dividend.magnitude, divisor.magnitude)
            if (cmp < 0) return ZERO
            if (cmp == 0) return NativeInteger(
                uintArrayOf(1u),
                dividend.sign * divisor.sign
            )
            val (quotMag, _) = divMagnitudes(
                dividend.magnitude,
                divisor.magnitude
            )
            val trimmed = trimLeadingZeros(quotMag)
            if (trimmed.isEmpty()) return ZERO
            return NativeInteger(trimmed, dividend.sign * divisor.sign)
        }

        private fun trimLeadingZeros(mag: UIntArray): UIntArray {
            var len = mag.size
            while (len > 0 && mag[len - 1] == 0u) len--
            return if (len == mag.size) mag else mag.copyOf(len)
        }

        private fun compareMagnitudes(a: UIntArray, b: UIntArray): Int {
            if (a.size != b.size) return a.size.compareTo(b.size)
            for (i in a.indices.reversed()) {
                if (a[i] != b[i]) return a[i].compareTo(b[i])
            }
            return 0
        }

        private fun addMagnitudes(a: UIntArray, b: UIntArray): UIntArray {
            val len = maxOf(a.size, b.size)
            val result = UIntArray(len + 1)
            var carry = 0L
            for (i in 0 until len) {
                val sum =
                    (if (i < a.size) a[i].toLong() else 0L) +
                            (if (i < b.size) b[i].toLong() else 0L) +
                            carry
                result[i] = sum.toUInt()
                carry = sum ushr 32
            }
            result[len] = carry.toUInt()
            return trimLeadingZeros(result)
        }

        private fun subtractMagnitudes(
            larger: UIntArray,
            smaller: UIntArray
        ): UIntArray {
            val result = UIntArray(larger.size)
            var borrow = 0L
            for (i in larger.indices) {
                val diff =
                    larger[i].toLong() -
                            (if (i < smaller.size) smaller[i].toLong()
                            else 0L) -
                            borrow
                result[i] = diff.toUInt()
                borrow = if (diff < 0L) 1L else 0L
            }
            return trimLeadingZeros(result)
        }

        private fun multiplyMagnitudes(a: UIntArray, b: UIntArray): UIntArray {
            if (a.isEmpty() || b.isEmpty()) return UIntArray(0)
            val result = UIntArray(a.size + b.size)
            for (i in a.indices) {
                var carry = 0L
                for (j in b.indices) {
                    val prod =
                        a[i].toLong() * b[j].toLong() +
                                result[i + j].toLong() +
                                carry
                    result[i + j] = prod.toUInt()
                    carry = prod ushr 32
                }
                result[i + b.size] = carry.toUInt()
            }
            return trimLeadingZeros(result)
        }

        private fun bitLength(mag: UIntArray): Int {
            if (mag.isEmpty()) return 0
            return (mag.size - 1) * 32 + (32 - mag.last()
                .countLeadingZeroBits())
        }

        private fun shiftLeftMag(mag: UIntArray, bits: Int): UIntArray {
            if (bits == 0 || mag.isEmpty()) return mag
            val limbShift = bits / 32
            val bitShift = bits % 32
            val result = UIntArray(mag.size + limbShift + 1)
            for (i in mag.indices) {
                val v = mag[i].toLong()
                result[i + limbShift] = (
                        result[i + limbShift].toLong() or
                                ((v shl bitShift) and 0xFFFFFFFFL)
                        ).toUInt()
                if (bitShift > 0)
                    result[i + limbShift + 1] = (
                            result[i + limbShift + 1].toLong() or
                                    (v ushr (32 - bitShift))
                            ).toUInt()
            }
            return trimLeadingZeros(result)
        }

        private fun shiftRightMag(mag: UIntArray, bits: Int): UIntArray {
            if (bits == 0 || mag.isEmpty()) return mag
            val limbShift = bits / 32
            val bitShift = bits % 32
            if (limbShift >= mag.size) return UIntArray(0)
            val result = UIntArray(mag.size - limbShift)
            for (i in result.indices) {
                val lo =
                    mag[i + limbShift].toLong() ushr bitShift
                val hi =
                    if (bitShift > 0 && i + limbShift + 1 < mag.size)
                        (mag[i + limbShift + 1].toLong()
                                shl (32 - bitShift)) and 0xFFFFFFFFL
                    else 0L
                result[i] = (lo or hi).toUInt()
            }
            return trimLeadingZeros(result)
        }

        private fun divMagnitudes(
            dividend: UIntArray,
            divisor: UIntArray
        ): Pair<UIntArray, UIntArray> {
            val cmp = compareMagnitudes(dividend, divisor)
            if (cmp < 0) return Pair(UIntArray(0), dividend.copyOf())
            if (cmp == 0) return Pair(uintArrayOf(1u), UIntArray(0))
            val shift = bitLength(dividend) - bitLength(divisor)
            val quotMag = UIntArray(shift / 32 + 1)
            var remainder = dividend.copyOf()
            var shifted = shiftLeftMag(divisor, shift)
            for (i in shift downTo 0) {
                if (compareMagnitudes(remainder, shifted) >= 0) {
                    remainder = subtractMagnitudes(remainder, shifted)
                    quotMag[i / 32] = (
                            quotMag[i / 32].toLong() or
                                    (1L shl (i % 32))
                            ).toUInt()
                }
                if (i > 0) shifted = shiftRightMag(shifted, 1)
            }
            return Pair(trimLeadingZeros(quotMag), trimLeadingZeros(remainder))
        }

        private fun divideByTen(mag: UIntArray): Pair<UIntArray, Int> {
            val result = UIntArray(mag.size)
            var rem = 0L
            for (i in mag.indices.reversed()) {
                val cur = (rem shl 32) or mag[i].toLong()
                result[i] = (cur / 10L).toUInt()
                rem = cur % 10L
            }
            return Pair(trimLeadingZeros(result), rem.toInt())
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
        val magCmp: Int = compareMagnitudes(this.magnitude, oth.magnitude)
        return if (this.sign == IntegerSign.Positive) magCmp else -magCmp
    }

    // ------------------------- Arithmetic operations -------------------------

    override fun unaryMinus(): PlatformInteger =
        NativeInteger(this.magnitude, -this.sign)

    override fun plus(other: PlatformInteger): PlatformInteger {
        val oth = other as NativeInteger
        if (this.sign == IntegerSign.Zero) return oth
        if (oth.sign == IntegerSign.Zero) return this
        if (this.sign == oth.sign) return NativeInteger(
            addMagnitudes(this.magnitude, oth.magnitude),
            this.sign
        )
        val cmp: Int = compareMagnitudes(this.magnitude, oth.magnitude)
        return when {
            cmp == 0 -> ZERO
            cmp > 0 -> NativeInteger(
                subtractMagnitudes(this.magnitude, oth.magnitude),
                this.sign
            )
            else -> NativeInteger(
                subtractMagnitudes(oth.magnitude, this.magnitude),
                oth.sign
            )
        }
    }

    override fun minus(other: PlatformInteger): PlatformInteger =
        this + (-other)

    override fun times(other: PlatformInteger): PlatformInteger {
        val oth = other as NativeInteger
        if (this.sign == IntegerSign.Zero || oth.sign == IntegerSign.Zero)
            return ZERO
        val mag: UIntArray = multiplyMagnitudes(this.magnitude, oth.magnitude)
        return NativeInteger(mag, sign = this.sign * oth.sign)
    }

    override fun div(other: PlatformInteger): PlatformInteger {
        val divisor = other as NativeInteger
        val remainder = (this % other) as NativeInteger
        val exactDividend = (this + (-remainder)) as NativeInteger
        return divTruncated(exactDividend, divisor)
    }

    override fun rem(other: PlatformInteger): PlatformInteger {
        val divisor = (other as NativeInteger).abs()
        if (divisor.sign == IntegerSign.Zero)
            throw ArithmeticException("Division by zero")
        if (this.sign == IntegerSign.Zero) return ZERO
        val cmp: Int = compareMagnitudes(this.magnitude, divisor.magnitude)
        if (cmp == 0) return ZERO
        val (_, remMag) = divMagnitudes(this.magnitude, divisor.magnitude)
        val trimmed: UIntArray = trimLeadingZeros(remMag)
        if (trimmed.isEmpty()) return ZERO
        if (this.sign == IntegerSign.Positive)
            return NativeInteger(trimmed, IntegerSign.Positive)
        val truncRemainder = NativeInteger(trimmed, IntegerSign.Positive)
        return (divisor + (-truncRemainder)) as NativeInteger

    }

    private fun abs(): NativeInteger =
        if (this.sign == IntegerSign.Negative)
            NativeInteger(magnitude, IntegerSign.Positive)
        else this

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String {
        if (this.sign == IntegerSign.Zero) return "0"
        val builder = StringBuilder()
        var remaining: UIntArray = this.magnitude.copyOf()
        while (remaining.isNotEmpty()) {
            val (quotient: UIntArray, digit: Int) = divideByTen(remaining)
            builder.append(digit)
            remaining = trimLeadingZeros(quotient)
        }
        if (this.sign == IntegerSign.Negative) builder.append('-')
        return builder.toString()
            .reversed()
    }
}
