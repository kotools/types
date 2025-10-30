package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
public class IntegerJavaSample {
    // -------------------- Structural equality operations ---------------------

    @Test
    void equalsOverride() {
        final long number = Long.MAX_VALUE;
        final Integer x = Integer.from(number);
        final Integer y = Integer.from(number);
        final boolean result = x.equals(y);
        if (!result) throw new IllegalStateException("Check failed.");
    }

    @Test
    void hashCodeOverride() {
        final long number = Long.MAX_VALUE;
        final Integer x = Integer.from(number);
        final Integer y = Integer.from(number);
        final boolean result = x.hashCode() == y.hashCode();
        if (!result) throw new IllegalStateException("Check failed.");
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    void plus() {
        final Integer x = Integer.from(9223372036854775807L);
        final Integer y = Integer.from(2);
        final Integer sum = x.plus(y);
        final Integer expected = Integer.parse("9223372036854775809");
        final boolean result = sum.equals(expected);
        if (!result) throw new IllegalStateException("Check failed.");
    }

    @Test
    void minus() {
        final Integer x = Integer.from(-9223372036854775807L);
        final Integer y = Integer.from(2);
        final Integer difference = x.minus(y);
        final Integer expected = Integer.parse("-9223372036854775809");
        final boolean result = difference.equals(expected);
        if (!result) throw new IllegalStateException("Check failed.");
    }

    @Test
    void times() {
        final Integer x = Integer.from(9223372036854775807L);
        final Integer y = Integer.from(10);
        final Integer product = x.times(y);
        final Integer expected = Integer.parse("92233720368547758070");
        final boolean result = product.equals(expected);
        if (!result) throw new IllegalStateException("Check failed.");
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    void toStringOverride() {
        final long number = 9223372036854775807L;
        final String integerString = Integer.from(number)
                .toString();
        final String expected = String.valueOf(number);
        final boolean result = integerString.equals(expected);
        if (!result) throw new IllegalStateException("Check failed.");
    }

    // ----------------------- Class-level declarations ------------------------

    @Test
    void from() {
        // Given
        final long number = 9_223_372_036_854_775_807L;
        // When
        final Integer integer = Integer.from(number);
        // Then
        final String result = integer.toString();
        final String expected = String.valueOf(number);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void parseWithDecimalString() {
        // Given
        final String text = "1234";
        // When
        final Integer integer = Integer.parse(text);
        // Then
        final String result = integer.toString();
        Assertions.assertEquals(text, result);
    }
}
