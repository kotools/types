package kotools.types.number;

import org.junit.jupiter.api.Test;
import org.kotools.types.Assert;

class StrictlyNegativeDoubleJavaSampleTest {
    private final StrictlyNegativeDoubleJavaSample sample =
            new StrictlyNegativeDoubleJavaSample();

    @Test
    void equals_should_pass() {
        Assert.printsTrue(this.sample::equalsOverride);
    }

    @Test
    void hashCode_should_pass() {
        Assert.printsTrue(this.sample::hashCodeOverride);
    }

    @Test
    void toDouble_should_pass() {
        final String expected = "-7.0";
        Assert.prints(expected, this.sample::toDouble);
    }

    @Test
    void toString_should_pass() {
        final String expected = "-23.0";
        Assert.prints(expected, this.sample::toStringOverride);
    }
}
