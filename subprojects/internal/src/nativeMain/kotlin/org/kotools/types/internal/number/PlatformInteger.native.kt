package org.kotools.types.internal.number

import org.kotools.types.internal.InternalKotoolsTypesApi

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
    private val magnitude: IntArray, // little-endian base-2^32, no leading zeros
    private val sign: IntegerSign
) : PlatformInteger {
    // ----------------------- Class-level declarations ------------------------

    companion object {
        // ------------------ Constants and factory functions ------------------

        private val Zero: NativeInteger by lazy {
            val magnitude = IntArray(size = 0)
            NativeInteger(magnitude, IntegerSign.Zero)
        }

        fun fromLong(value: Long): NativeInteger {
            if (value == 0L) return Zero
            val sign: IntegerSign =
                if (value > 0L) IntegerSign.Positive
                else IntegerSign.Negative
            if (value == Long.MIN_VALUE) {
                return NativeInteger(
                    intArrayOf(0, Int.MIN_VALUE),
                    IntegerSign.Negative
                ) // 2^63 = [0, 2^31] in base-2^32
            }
            val absVal = if (value < 0L) -value else value
            val lo = (absVal and 0xFFFFFFFFL).toInt()
            val hi = (absVal ushr 32).toInt()
            val mag = if (hi != 0) intArrayOf(lo, hi) else intArrayOf(lo)
            return NativeInteger(mag, sign)
        }

        fun parse(value: String): NativeInteger {
            val negative = value[0] == '-'
            val digits = if (negative) value.substring(1) else value
            if (digits == "0") return Zero
            var mag = IntArray(0)
            for (ch in digits) {
                mag = addMagnitudes(
                    multiplyMagnitudes(mag, intArrayOf(10)),
                    intArrayOf(ch.digitToInt())
                )
            }
            val trimmed = trimLeadingZeros(mag)
            val sign: IntegerSign = when {
                trimmed.isEmpty() -> IntegerSign.Zero
                negative -> IntegerSign.Negative
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
            if (dividend.sign == IntegerSign.Zero) return Zero
            val cmp = compareMagnitudes(dividend.magnitude, divisor.magnitude)
            if (cmp < 0) return Zero
            if (cmp == 0) return NativeInteger(
                intArrayOf(1),
                dividend.sign * divisor.sign
            )
            val (quotMag, _) = divMagnitudes(
                dividend.magnitude,
                divisor.magnitude
            )
            val trimmed = trimLeadingZeros(quotMag)
            if (trimmed.isEmpty()) return Zero
            return NativeInteger(trimmed, dividend.sign * divisor.sign)
        }

        private fun trimLeadingZeros(mag: IntArray): IntArray {
            var len = mag.size
            while (len > 0 && mag[len - 1] == 0) len--
            return if (len == mag.size) mag else mag.copyOf(len)
        }

        private fun compareMagnitudes(a: IntArray, b: IntArray): Int {
            if (a.size != b.size) return a.size.compareTo(b.size)
            for (i in a.indices.reversed()) {
                val au = a[i].toUInt()
                val bu = b[i].toUInt()
                if (au != bu) return au.compareTo(bu)
            }
            return 0
        }

        private fun addMagnitudes(a: IntArray, b: IntArray): IntArray {
            val len = maxOf(a.size, b.size)
            val result = IntArray(len + 1)
            var carry = 0L
            for (i in 0 until len) {
                val sum =
                    (if (i < a.size) a[i].toLong() and 0xFFFFFFFFL else 0L) +
                    (if (i < b.size) b[i].toLong() and 0xFFFFFFFFL else 0L) +
                    carry
                result[i] = (sum and 0xFFFFFFFFL).toInt()
                carry = sum ushr 32
            }
            result[len] = carry.toInt()
            return trimLeadingZeros(result)
        }

        private fun subtractMagnitudes(
            larger: IntArray,
            smaller: IntArray
        ): IntArray {
            val result = IntArray(larger.size)
            var borrow = 0L
            for (i in larger.indices) {
                val diff =
                    (larger[i].toLong() and 0xFFFFFFFFL) -
                    (if (i < smaller.size) smaller[i].toLong() and 0xFFFFFFFFL
                     else 0L) -
                    borrow
                result[i] = (diff and 0xFFFFFFFFL).toInt()
                borrow = if (diff < 0L) 1L else 0L
            }
            return trimLeadingZeros(result)
        }

        private fun multiplyMagnitudes(a: IntArray, b: IntArray): IntArray {
            if (a.isEmpty() || b.isEmpty()) return IntArray(0)
            val result = IntArray(a.size + b.size)
            for (i in a.indices) {
                var carry = 0L
                for (j in b.indices) {
                    val prod =
                        (a[i].toLong() and 0xFFFFFFFFL) *
                        (b[j].toLong() and 0xFFFFFFFFL) +
                        (result[i + j].toLong() and 0xFFFFFFFFL) +
                        carry
                    result[i + j] = (prod and 0xFFFFFFFFL).toInt()
                    carry = prod ushr 32
                }
                result[i + b.size] = carry.toInt()
            }
            return trimLeadingZeros(result)
        }

        private fun bitLength(mag: IntArray): Int {
            if (mag.isEmpty()) return 0
            return (mag.size - 1) * 32 + (32 - mag.last().countLeadingZeroBits())
        }

        private fun shiftLeftMag(mag: IntArray, bits: Int): IntArray {
            if (bits == 0 || mag.isEmpty()) return mag
            val limbShift = bits / 32
            val bitShift = bits % 32
            val result = IntArray(mag.size + limbShift + 1)
            for (i in mag.indices) {
                val v = mag[i].toLong() and 0xFFFFFFFFL
                result[i + limbShift] = (
                    (result[i + limbShift].toLong() and 0xFFFFFFFFL) or
                    ((v shl bitShift) and 0xFFFFFFFFL)
                ).toInt()
                if (bitShift > 0)
                    result[i + limbShift + 1] = (
                        (result[i + limbShift + 1].toLong() and 0xFFFFFFFFL) or
                        (v ushr (32 - bitShift))
                    ).toInt()
            }
            return trimLeadingZeros(result)
        }

        private fun shiftRightMag(mag: IntArray, bits: Int): IntArray {
            if (bits == 0 || mag.isEmpty()) return mag
            val limbShift = bits / 32
            val bitShift = bits % 32
            if (limbShift >= mag.size) return IntArray(0)
            val result = IntArray(mag.size - limbShift)
            for (i in result.indices) {
                val lo = (mag[i + limbShift].toLong() and 0xFFFFFFFFL) ushr bitShift
                val hi =
                    if (bitShift > 0 && i + limbShift + 1 < mag.size)
                        ((mag[i + limbShift + 1].toLong() and 0xFFFFFFFFL)
                            shl (32 - bitShift)) and 0xFFFFFFFFL
                    else 0L
                result[i] = (lo or hi).toInt()
            }
            return trimLeadingZeros(result)
        }

        private fun divMagnitudes(
            dividend: IntArray,
            divisor: IntArray
        ): Pair<IntArray, IntArray> {
            val cmp = compareMagnitudes(dividend, divisor)
            if (cmp < 0) return Pair(IntArray(0), dividend.copyOf())
            if (cmp == 0) return Pair(intArrayOf(1), IntArray(0))
            val shift = bitLength(dividend) - bitLength(divisor)
            val quotMag = IntArray(shift / 32 + 1)
            var remainder = dividend.copyOf()
            var shifted = shiftLeftMag(divisor, shift)
            for (i in shift downTo 0) {
                if (compareMagnitudes(remainder, shifted) >= 0) {
                    remainder = subtractMagnitudes(remainder, shifted)
                    quotMag[i / 32] = (
                        (quotMag[i / 32].toLong() and 0xFFFFFFFFL) or
                        (1L shl (i % 32))
                    ).toInt()
                }
                if (i > 0) shifted = shiftRightMag(shifted, 1)
            }
            return Pair(trimLeadingZeros(quotMag), trimLeadingZeros(remainder))
        }

        private fun divideByTen(mag: IntArray): Pair<IntArray, Int> {
            val result = IntArray(mag.size)
            var rem = 0L
            for (i in mag.indices.reversed()) {
                val cur = (rem shl 32) or (mag[i].toLong() and 0xFFFFFFFFL)
                result[i] = (cur / 10L).toInt()
                rem = cur % 10L
            }
            return Pair(trimLeadingZeros(result), rem.toInt())
        }
    }

    // ------------------------------ Comparisons ------------------------------

    override fun equals(other: Any?): Boolean {
        if (other !is NativeInteger) return false
        return this.sign == other.sign && this.magnitude.contentEquals(other.magnitude)
    }

    override fun hashCode(): Int =
        31 * sign.hashCode() + magnitude.contentHashCode()

    override fun compareTo(other: PlatformInteger): Int {
        check(other is NativeInteger)
        if (this.sign != other.sign) return this.sign.compareTo(other.sign)
        if (this.sign == IntegerSign.Zero) return 0
        val magCmp: Int = compareMagnitudes(this.magnitude, other.magnitude)
        return if (this.sign == IntegerSign.Positive) magCmp else -magCmp
    }

    // ------------------------- Arithmetic operations -------------------------

    override fun unaryMinus(): PlatformInteger =
        NativeInteger(this.magnitude, -this.sign)

    override fun plus(other: PlatformInteger): PlatformInteger {
        val o = other as NativeInteger
        if (this.sign == IntegerSign.Zero) return o
        if (o.sign == IntegerSign.Zero) return this
        return if (this.sign == o.sign) {
            NativeInteger(addMagnitudes(this.magnitude, o.magnitude), this.sign)
        } else {
            val cmp = compareMagnitudes(this.magnitude, o.magnitude)
            when {
                cmp == 0 -> Zero
                cmp > 0 -> NativeInteger(
                    subtractMagnitudes(this.magnitude, o.magnitude),
                    this.sign
                )
                else -> NativeInteger(
                    subtractMagnitudes(o.magnitude, this.magnitude),
                    o.sign
                )
            }
        }
    }

    override fun minus(other: PlatformInteger): PlatformInteger =
        this + (-other)

    override fun times(other: PlatformInteger): PlatformInteger {
        val o = other as NativeInteger
        if (this.sign == IntegerSign.Zero || o.sign == IntegerSign.Zero)
            return Zero
        val mag = multiplyMagnitudes(this.magnitude, o.magnitude)
        return NativeInteger(mag, this.sign * o.sign)
    }

    override fun div(other: PlatformInteger): PlatformInteger {
        val divisor = other as NativeInteger
        val remainder = (this.rem(other)) as NativeInteger
        val exactDividend = (this + (-remainder)) as NativeInteger
        return divTruncated(exactDividend, divisor)
    }

    override fun rem(other: PlatformInteger): PlatformInteger {
        val divisor = (other as NativeInteger).abs()
        if (divisor.sign == IntegerSign.Zero)
            throw ArithmeticException("Division by zero")
        if (this.sign == IntegerSign.Zero) return Zero
        val cmp = compareMagnitudes(this.magnitude, divisor.magnitude)
        if (cmp == 0) return Zero
        val (_, remMag) = divMagnitudes(this.magnitude, divisor.magnitude)
        val trimmed = trimLeadingZeros(remMag)
        if (trimmed.isEmpty()) return Zero
        return if (this.sign == IntegerSign.Negative) {
            val truncRemainder = NativeInteger(trimmed, IntegerSign.Positive)
            (divisor + (-truncRemainder)) as NativeInteger
        } else {
            NativeInteger(trimmed, IntegerSign.Positive)
        }
    }

    private fun abs(): NativeInteger =
        if (sign < IntegerSign.Zero)
            NativeInteger(magnitude, IntegerSign.Positive)
        else this

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String {
        if (sign == IntegerSign.Zero) return "0"
        val sb = StringBuilder()
        var remaining = magnitude.copyOf()
        while (remaining.isNotEmpty()) {
            val (quotient, digit) = divideByTen(remaining)
            sb.append(digit)
            remaining = trimLeadingZeros(quotient)
        }
        if (sign == IntegerSign.Negative) sb.append('-')
        return sb.toString().reversed()
    }
}
