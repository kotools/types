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
    private val magnitude: LongArray, // little-endian base-2^32, no leading zeros
    private val sign: IntegerSign
) : PlatformInteger {
    // ----------------------- Class-level declarations ------------------------

    companion object {
        // ------------------ Constants and factory functions ------------------

        private val Zero: NativeInteger by lazy {
            val magnitude = LongArray(size = 0)
            NativeInteger(magnitude, IntegerSign.Zero)
        }

        fun fromLong(value: Long): NativeInteger {
            if (value == 0L) return Zero
            val sign: IntegerSign =
                if (value > 0L) IntegerSign.Positive
                else IntegerSign.Negative
            if (value == Long.MIN_VALUE) {
                return NativeInteger(
                    longArrayOf(0L, 0x80000000L),
                    IntegerSign.Negative
                ) // 2^63 = [0, 2^31] in base-2^32
            }
            val absVal = if (value < 0L) -value else value
            val lo = absVal and 0xFFFFFFFFL
            val hi = absVal ushr 32
            val mag = if (hi != 0L) longArrayOf(lo, hi) else longArrayOf(lo)
            return NativeInteger(mag, sign)
        }

        fun parse(value: String): NativeInteger {
            val negative = value[0] == '-'
            val digits = if (negative) value.substring(1) else value
            if (digits == "0") return Zero
            var mag = LongArray(0)
            for (ch in digits) {
                mag = addMagnitudes(
                    multiplyMagnitudes(mag, longArrayOf(10L)),
                    longArrayOf(ch.digitToInt().toLong())
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
                longArrayOf(1L),
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

        private fun trimLeadingZeros(mag: LongArray): LongArray {
            var len = mag.size
            while (len > 0 && mag[len - 1] == 0L) len--
            return if (len == mag.size) mag else mag.copyOf(len)
        }

        private fun compareMagnitudes(a: LongArray, b: LongArray): Int {
            if (a.size != b.size) return a.size.compareTo(b.size)
            for (i in a.indices.reversed()) {
                if (a[i] != b[i]) return a[i].compareTo(b[i])
            }
            return 0
        }

        private fun addMagnitudes(a: LongArray, b: LongArray): LongArray {
            val len = maxOf(a.size, b.size)
            val result = LongArray(len + 1)
            var carry = 0L
            for (i in 0 until len) {
                val sum = (if (i < a.size) a[i] else 0L) +
                        (if (i < b.size) b[i] else 0L) + carry
                result[i] = sum and 0xFFFFFFFFL
                carry = sum ushr 32
            }
            result[len] = carry
            return trimLeadingZeros(result)
        }

        private fun subtractMagnitudes(
            larger: LongArray,
            smaller: LongArray
        ): LongArray {
            val result = LongArray(larger.size)
            var borrow = 0L
            for (i in larger.indices) {
                val diff =
                    larger[i] - (if (i < smaller.size) smaller[i] else 0L) - borrow
                result[i] = diff and 0xFFFFFFFFL
                borrow = if (diff < 0L) 1L else 0L
            }
            return trimLeadingZeros(result)
        }

        private fun multiplyMagnitudes(a: LongArray, b: LongArray): LongArray {
            if (a.isEmpty() || b.isEmpty()) return LongArray(0)
            val result = LongArray(a.size + b.size)
            for (i in a.indices) {
                var carry = 0L
                for (j in b.indices) {
                    val prod = a[i] * b[j] + result[i + j] + carry
                    result[i + j] = prod and 0xFFFFFFFFL
                    carry = prod ushr 32
                }
                result[i + b.size] += carry
            }
            return trimLeadingZeros(result)
        }

        private fun bitLength(mag: LongArray): Int {
            if (mag.isEmpty()) return 0
            return (mag.size - 1) * 32 + (32 - mag.last().toInt()
                .countLeadingZeroBits())
        }

        private fun shiftLeftMag(mag: LongArray, bits: Int): LongArray {
            if (bits == 0 || mag.isEmpty()) return mag
            val limbShift = bits / 32
            val bitShift = bits % 32
            val result = LongArray(mag.size + limbShift + 1)
            for (i in mag.indices) {
                val v = mag[i]
                result[i + limbShift] =
                    result[i + limbShift] or ((v shl bitShift) and 0xFFFFFFFFL)
                if (bitShift > 0)
                    result[i + limbShift + 1] =
                        result[i + limbShift + 1] or (v ushr (32 - bitShift))
            }
            return trimLeadingZeros(result)
        }

        private fun shiftRightMag(mag: LongArray, bits: Int): LongArray {
            if (bits == 0 || mag.isEmpty()) return mag
            val limbShift = bits / 32
            val bitShift = bits % 32
            if (limbShift >= mag.size) return LongArray(0)
            val result = LongArray(mag.size - limbShift)
            for (i in result.indices) {
                val lo = mag[i + limbShift] ushr bitShift
                val hi = if (bitShift > 0 && i + limbShift + 1 < mag.size)
                    (mag[i + limbShift + 1] shl (32 - bitShift)) and 0xFFFFFFFFL
                else 0L
                result[i] = lo or hi
            }
            return trimLeadingZeros(result)
        }

        private fun divMagnitudes(
            dividend: LongArray,
            divisor: LongArray
        ): Pair<LongArray, LongArray> {
            val cmp = compareMagnitudes(dividend, divisor)
            if (cmp < 0) return Pair(LongArray(0), dividend.copyOf())
            if (cmp == 0) return Pair(longArrayOf(1L), LongArray(0))
            val shift = bitLength(dividend) - bitLength(divisor)
            val quotMag = LongArray(shift / 32 + 1)
            var remainder = dividend.copyOf()
            var shifted = shiftLeftMag(divisor, shift)
            for (i in shift downTo 0) {
                if (compareMagnitudes(remainder, shifted) >= 0) {
                    remainder = subtractMagnitudes(remainder, shifted)
                    quotMag[i / 32] = quotMag[i / 32] or (1L shl (i % 32))
                }
                if (i > 0) shifted = shiftRightMag(shifted, 1)
            }
            return Pair(trimLeadingZeros(quotMag), trimLeadingZeros(remainder))
        }

        private fun divideByTen(mag: LongArray): Pair<LongArray, Int> {
            val result = LongArray(mag.size)
            var rem = 0L
            for (i in mag.indices.reversed()) {
                val cur = (rem shl 32) or mag[i]
                result[i] = cur / 10L
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
