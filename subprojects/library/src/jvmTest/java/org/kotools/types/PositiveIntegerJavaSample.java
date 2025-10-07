package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
public class PositiveIntegerJavaSample {
    @Test
    void equalsOverride() {
        final String text = "123456789";
        final PositiveInteger integer = PositiveInteger.Companion.of(text);
        Assertions.assertNotNull(integer);
        final PositiveInteger other = PositiveInteger.Companion.of(text);
        Assertions.assertNotNull(other);
        final boolean result = integer.equals(other);
        Assertions.assertTrue(result);
    }

    @Test
    void hashCodeOverride() {
        final String text = "123456789";
        final PositiveInteger integer = PositiveInteger.Companion.of(text);
        Assertions.assertNotNull(integer);
        final int hashCode = integer.hashCode();
        final PositiveInteger otherInteger = PositiveInteger.Companion.of(text);
        Assertions.assertNotNull(otherInteger);
        final int otherHashCode = otherInteger.hashCode();
        Assertions.assertEquals(hashCode, otherHashCode);
    }

    @Test
    void plus() {
        final PositiveInteger integer =
                PositiveInteger.Companion.of("123456789");
        Assertions.assertNotNull(integer);
        final String text = Long.valueOf(Long.MAX_VALUE)
                .toString();
        final PositiveInteger other = PositiveInteger.Companion.of(text);
        Assertions.assertNotNull(other);
        final PositiveInteger result = integer.plus(other);
        final PositiveInteger expected =
                PositiveInteger.Companion.of("9223372036978232596");
        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void toStringOverride() {
        final String text = "123456789";
        final PositiveInteger integer = PositiveInteger.Companion.of(text);
        Assertions.assertNotNull(integer);
        final String result = integer.toString();
        Assertions.assertEquals(text, result);
    }

    // ------------------------------- Companion -------------------------------

    @Test
    void of() {
        final PositiveInteger result = PositiveInteger.Companion.of("123456");
        Assertions.assertNotNull(result);
    }

    @Test
    void minimum() {
        final PositiveInteger result = PositiveInteger.Companion.minimum();
        final PositiveInteger expected = PositiveInteger.Companion.of("1");
        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected, result);
    }
}
