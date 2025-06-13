package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

import java.util.Arrays;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
class ZeroJavaSample {
    @Test
    void equalsOverride() {
        final Zero first = new Zero();
        final Zero second = new Zero();
        final boolean actual = first.equals(second);
        Assertions.assertTrue(actual);
    }

    @Test
    void hashCodeOverride() {
        final int first = new Zero()
                .hashCode();
        final int second = new Zero()
                .hashCode();
        Assertions.assertEquals(first, second);
    }

    @Test
    void compareToByte() {
        final Zero zero = new Zero();
        final byte other = 1;
        final boolean actual = zero.compareTo(other) < 0;
        Assertions.assertTrue(actual);
    }

    @Test
    void compareToShort() {
        final Zero zero = new Zero();
        final short other = 1;
        final boolean actual = zero.compareTo(other) < 0;
        Assertions.assertTrue(actual);
    }

    @Test
    void compareToInt() {
        final Zero zero = new Zero();
        final int other = 1;
        final boolean actual = zero.compareTo(other) < 0;
        Assertions.assertTrue(actual);
    }

    @Test
    void compareToLong() {
        final Zero zero = new Zero();
        final long other = 1;
        final boolean actual = zero.compareTo(other) < 0;
        Assertions.assertTrue(actual);
    }

    @Test
    void compareToFloat() {
        final Zero zero = new Zero();
        final float other = 0.01f;
        final boolean actual = zero.compareTo(other) < 0;
        Assertions.assertTrue(actual);
    }

    @Test
    void compareToDouble() {
        final Zero zero = new Zero();
        final double other = 0.01;
        final boolean actual = zero.compareTo(other) < 0;
        Assertions.assertTrue(actual);
    }

    @Test
    void toByte() {
        final Zero zero = new Zero();
        final byte actual = zero.toByte();
        final byte expected = 0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toShort() {
        final short actual = new Zero()
                .toShort();
        final short expected = 0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toInt() {
        final int actual = new Zero()
                .toInt();
        final int expected = 0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toLong() {
        final long actual = new Zero()
                .toLong();
        final long expected = 0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toFloat() {
        final float actual = new Zero()
                .toFloat();
        final float expected = 0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toDouble() {
        final double actual = new Zero()
                .toDouble();
        final double expected = 0.0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toChar() {
        final char actual = new Zero()
                .toChar();
        final char expected = '0';
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toStringOverride() {
        final Zero zero = new Zero();
        final String actual = zero.toString();
        final String expected = "0";
        Assertions.assertEquals(expected, actual);
    }

    // ------------------------------- Companion -------------------------------

    @Test
    void orThrowWithByte() {
        final byte number = 0;
        boolean isSuccess;
        try {
            Zero.orThrow(number);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void orThrowWithShort() {
        final short number = 0;
        boolean isSuccess;
        try {
            Zero.orThrow(number);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void orThrowWithInt() {
        final int number = 0;
        boolean isSuccess;
        try {
            Zero.orThrow(number);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void orThrowWithLong() {
        final long number = 0;
        boolean isSuccess;
        try {
            Zero.orThrow(number);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void orThrowWithFloat() {
        final float number = 0;
        boolean isSuccess;
        try {
            Zero.orThrow(number);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void orThrowWithDouble() {
        final double number = 0;
        boolean isSuccess;
        try {
            Zero.orThrow(number);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void orThrowWithString() {
        boolean isSuccess;
        try {
            Arrays.asList("0", "000", "0.0", "0.000", "000.0", "000.000")
                    .forEach(Zero::orThrow);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }
}
