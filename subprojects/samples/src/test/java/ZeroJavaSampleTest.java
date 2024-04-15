package org.kotools.types;

import org.junit.jupiter.api.Test;

class ZeroJavaSampleTest {
    @Test
    void isEqualTo_Byte_should_pass() {
        final ZeroJavaSample sample = new ZeroJavaSample();
        Assert.printsTrue(sample::isEqualTo_Byte);
    }

    @Test
    void toByte_should_pass() {
        final ZeroJavaSample sample = new ZeroJavaSample();
        final String expected = "0";
        Assert.prints(expected, sample::toByte);
    }

    @Test
    void toStringSample_should_pass() {
        final ZeroJavaSample sample = new ZeroJavaSample();
        final String expected = "0";
        Assert.prints(expected, sample::toStringSample);
    }
}
