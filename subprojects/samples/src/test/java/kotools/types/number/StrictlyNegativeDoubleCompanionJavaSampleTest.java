package kotools.types.number;

import org.junit.jupiter.api.Test;
import org.kotools.types.Assert;

class StrictlyNegativeDoubleCompanionJavaSampleTest {
    @Test
    void create_should_pass() {
        final String expected = "Success";
        final StrictlyNegativeDoubleCompanionJavaSample sample =
                new StrictlyNegativeDoubleCompanionJavaSample();
        Assert.prints(expected, sample::create);
    }
}
