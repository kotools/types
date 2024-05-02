package org.kotools.types;

import org.junit.jupiter.api.Test;

class ZeroJavaSampleTest {
    @Test
    void equalsOverride_should_pass() {
        final ZeroJavaSample sample = new ZeroJavaSample();
        Assert.printsTrue(sample::equalsOverride);
    }

    @Test
    void hashCodeOverride_should_pass() {
        final ZeroJavaSample sample = new ZeroJavaSample();
        Assert.printsTrue(sample::hashCodeOverride);
    }

    @Test
    void toByte_should_pass() {
        final ZeroJavaSample sample = new ZeroJavaSample();
        final String expected = "0";
        Assert.prints(expected, sample::toByte);
    }

    @Test
    void toShort_should_pass() {
        final ZeroJavaSample sample = new ZeroJavaSample();
        Assert.printsTrue(sample::toShort);
    }

    @Test
    void toInt_should_pass() {
        final ZeroJavaSample sample = new ZeroJavaSample();
        Assert.printsTrue(sample::toInt);
    }

    @Test
    void toStringSample_should_pass() {
        final ZeroJavaSample sample = new ZeroJavaSample();
        final String expected = "0";
        Assert.prints(expected, sample::toStringSample);
    }
}
