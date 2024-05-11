package org.kotools.types;

import org.junit.jupiter.api.Test;

class ZeroJavaSampleTest {
    private final ZeroJavaSample sample = new ZeroJavaSample();

    @Test
    void equalsOverride_should_pass() {
        Assert.printsTrue(this.sample::equalsOverride);
    }

    @Test
    void hashCodeOverride_should_pass() {
        Assert.printsTrue(this.sample::hashCodeOverride);
    }

    @Test
    void compareTo_Byte_should_pass() {
        Assert.printsTrue(this.sample::compareToByte);
    }

    @Test
    void toByte_should_pass() {
        final String expected = "0";
        Assert.prints(expected, this.sample::toByte);
    }

    @Test
    void toShort_should_pass() {
        Assert.printsTrue(this.sample::toShort);
    }

    @Test
    void toInt_should_pass() {
        Assert.printsTrue(this.sample::toInt);
    }

    @Test
    void toLong_should_pass() {
        Assert.printsTrue(this.sample::toLong);
    }

    @Test
    void toFloat_should_pass() {
        Assert.printsTrue(this.sample::toFloat);
    }

    @Test
    void toDouble_should_pass() {
        Assert.printsTrue(this.sample::toDouble);
    }

    @Test
    void toStringSample_should_pass() {
        final String expected = "0";
        Assert.prints(expected, this.sample::toStringSample);
    }
}
