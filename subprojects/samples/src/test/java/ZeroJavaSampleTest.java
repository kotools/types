package org.kotools.types;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ZeroJavaSampleTest {
    @Test
    public void toStringSample_should_pass() {
        final ZeroJavaSample sample = new ZeroJavaSample();
        final String actual = Assertions.assertDoesNotThrow(
                () -> SystemLambda.tapSystemOut(sample::toStringSample).trim()
        );
        final String expected = "0";
        Assertions.assertEquals(expected, actual);
    }
}
