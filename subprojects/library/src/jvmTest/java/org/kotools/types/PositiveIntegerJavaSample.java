package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

import java.util.Random;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
class PositiveIntegerJavaSample {
    @Test
    void equalsOverride() {
        final int number = new Random()
                .nextInt(1, Integer.MAX_VALUE);
        final PositiveInteger integer = PositiveInteger.orThrow(number);
        final PositiveInteger other = PositiveInteger.orThrow(number);
        final boolean equality = integer.equals(other);
        final String message =
                "Positive integers with the same value are equal.";
        Assertions.assertTrue(equality, message);
    }

    @Test
    void hashCodeOverride() {
        final int number = new Random()
                .nextInt(1, Integer.MAX_VALUE);
        final int hashCode = PositiveInteger.orThrow(number)
                .hashCode();
        final int other = PositiveInteger.orThrow(number)
                .hashCode();
        final boolean equality = hashCode == other;
        final String message =
                "Same positive integers have the same hash code value.";
        Assertions.assertTrue(equality, message);
    }

    @Test
    void toStringOverride() {
        final int number = new Random()
                .nextInt(1, Integer.MAX_VALUE);
        final String integerAsString = PositiveInteger.orThrow(number)
                .toString();
        final String expected = Integer.valueOf(number)
                .toString();
        Assertions.assertEquals(expected, integerAsString);
    }
}
