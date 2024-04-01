package org.kotools.types;

import org.junit.jupiter.api.Test;

public class ZeroJavaSampleTest {
    @Test
    public void toStringSample_should_pass() {
        final ZeroJavaSample sample = new ZeroJavaSample();
        final String expected = "0";
        Assert.prints(expected, sample::toStringSample);
    }
}
