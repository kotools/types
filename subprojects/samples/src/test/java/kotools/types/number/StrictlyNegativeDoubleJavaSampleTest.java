package kotools.types.number;

import org.junit.jupiter.api.Test;
import org.kotools.types.Assert;

class StrictlyNegativeDoubleJavaSampleTest {
    @Test
    void equals_should_pass() {
        final StrictlyNegativeDoubleJavaSample sample =
                new StrictlyNegativeDoubleJavaSample();
        Assert.printsTrue(sample::equalsOverride);
    }
}
