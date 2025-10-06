package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
public class PositiveIntegerJavaSample {
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
