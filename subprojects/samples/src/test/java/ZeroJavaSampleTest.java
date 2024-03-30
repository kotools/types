package org.kotools.types;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ZeroJavaSampleTest {
    @Test
    public void toStringSample_should_pass() {
        final ZeroJavaSample sample = new ZeroJavaSample();
        final String messagePrefix = "Java sample of 'Zero.toString()'";
        final String actual = Assertions.assertDoesNotThrow(
                () -> SystemLambda.tapSystemOut(sample::toStringSample).trim(),
                "%s shouldn't throw an exception.".formatted(messagePrefix)
        );
        final String expected = "0";
        Assertions.assertEquals(
                expected,
                actual,
                "%s should print '%s'.".formatted(messagePrefix, expected)
        );
    }
}
