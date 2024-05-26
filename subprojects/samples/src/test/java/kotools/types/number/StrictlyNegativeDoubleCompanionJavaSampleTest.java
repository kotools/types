package kotools.types.number;

import org.junit.jupiter.api.Test;
import org.kotools.types.Assert;

class StrictlyNegativeDoubleCompanionJavaSampleTest {
    @Test
    void create_should_pass() {
        final StrictlyNegativeDoubleCompanionJavaSample sample =
                new StrictlyNegativeDoubleCompanionJavaSample();
        Assert.printsTrue(sample::create);
    }

    @Test
    void createOrNull_should_pass() {
        final StrictlyNegativeDoubleCompanionJavaSample sample =
                new StrictlyNegativeDoubleCompanionJavaSample();
        Assert.printsTrue(sample::createOrNull);
    }
}
