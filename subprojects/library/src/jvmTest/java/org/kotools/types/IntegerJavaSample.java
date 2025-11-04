package org.kotools.types;

import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
public class IntegerJavaSample {
    // -------------------- Structural equality operations ---------------------

    @Test
    void equalsOverride() {
        // Given
        final long number = Long.MAX_VALUE;
        final Integer x = Integer.from(number);
        final Integer y = Integer.from(number);
        // When
        final boolean result = x.equals(y);
        // Then
        if (!result) throw new IllegalStateException("Check failed.");
    }

    @Test
    void hashCodeOverride() {
        // Given
        final long number = Long.MAX_VALUE;
        final Integer integer = Integer.from(number);
        // When
        final int result = integer.hashCode();
        // Then
        final int expected = Integer.from(number)
                .hashCode();
        final boolean check = result == expected;
        if (!check) throw new IllegalStateException("Check failed.");
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    void unaryMinus() {
        // Given
        final long number = 123456;
        final Integer x = Integer.from(number);
        // When
        final Integer result = x.unaryMinus();
        // Then
        final Integer expected = Integer.from(-number);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void plus() {
        // Given
        final Integer x = Integer.from(9223372036854775807L);
        final Integer y = Integer.from(2);
        // When
        final Integer result = x.plus(y);
        // Then
        final Integer expected = Integer.fromDecimal("9223372036854775809");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void minus() {
        // Given
        final Integer x = Integer.from(-9223372036854775807L);
        final Integer y = Integer.from(2);
        // When
        final Integer result = x.minus(y);
        // Then
        final Integer expected = Integer.fromDecimal("-9223372036854775809");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void times() {
        // Given
        final Integer x = Integer.from(9223372036854775807L);
        final Integer y = Integer.from(10);
        // When
        final Integer result = x.times(y);
        // Then
        final Integer expected = Integer.fromDecimal("92233720368547758070");
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void div() {
        // Given
        final Integer x = Integer.from(21);
        final Integer y = Integer.from(5);
        // When
        final Integer result = x.div(y);
        // Then
        final Integer expected = Integer.from(4);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void rem() {
        // Given
        final Integer x = Integer.from(22);
        final Integer y = Integer.from(5);
        // When
        final Integer result = x.rem(y);
        // Then
        final Integer expected = Integer.from(2);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    void toStringOverride() {
        // Given
        final long number = 9223372036854775807L;
        final Integer integer = Integer.from(number);
        // When
        final String result = integer.toString();
        // Then
        final String expected = String.valueOf(number);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    // ----------------------- Class-level declarations ------------------------

    @Test
    void from() {
        // Given
        final long number = 9223372036854775807L;
        // When
        final Integer result = Integer.from(number);
        // Then
        final String resultAsString = result.toString();
        final String expected = String.valueOf(number);
        final boolean check = resultAsString.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }

    @Test
    void fromDecimal() {
        // Given
        final long number = 123456L;
        final String text = String.valueOf(number);
        // When
        final Integer result = Integer.fromDecimal(text);
        // Then
        final Integer expected = Integer.from(number);
        final boolean check = result.equals(expected);
        if (!check) throw new IllegalStateException("Check failed.");
    }
}
