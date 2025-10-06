package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
public class PositiveIntegerJavaSample {
    @Test
    void equalsOverride() {
        final String text = "123456789";
        final PositiveInteger integer = PositiveInteger.of(text);
        Assertions.assertNotNull(integer);
        final PositiveInteger other = PositiveInteger.of(text);
        Assertions.assertNotNull(other);
        final boolean result = integer.equals(other);
        Assertions.assertTrue(result);
    }

    @Test
    void toStringOverride() {
        final String text = "123456789";
        final PositiveInteger integer = PositiveInteger.of(text);
        Assertions.assertNotNull(integer);
        final String result = integer.toString();
        Assertions.assertEquals(text, result);
    }

    // ------------------------------- Companion -------------------------------

    @Test
    void of() {
        final PositiveInteger actual = PositiveInteger.of("123456789");
        Assertions.assertNotNull(actual);
    }
}
