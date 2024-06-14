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
    void compareTo_Short_should_pass() {
        Assert.printsTrue(this.sample::compareToShort);
    }

    @Test
    void compareTo_Int_should_pass() {
        Assert.printsTrue(this.sample::compareToInt);
    }

    @Test
    void compareTo_Long_should_pass() {
        Assert.printsTrue(this.sample::compareToLong);
    }

    @Test
    void compareTo_Float_should_pass() {
        Assert.printsTrue(this.sample::compareToFloat);
    }

    @Test
    void compareTo_Double_should_pass() {
        Assert.printsTrue(this.sample::compareToDouble);
    }

    @Test
    void toByte_should_pass() {
        final int expected = 0;
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
    void toChar_should_pass() {
        Assert.printsTrue(this.sample::toChar);
    }

    @Test
    void toStringSample_should_pass() {
        final int expected = 0;
        Assert.prints(expected, this.sample::toStringSample);
    }
}
