package kotools.types.number;

import org.junit.jupiter.api.Test;
import org.kotools.types.Assert;

class NegativeIntCompanionJavaSampleTest {
    @Test
    void create_should_pass() {
        final String expected = "-7";
        final NegativeIntCompanionJavaSample sample =
                new NegativeIntCompanionJavaSample();
        Assert.prints(expected, sample::create);
    }

    @Test
    void createOrNull_should_pass() {
        final String expected = "-7";
        final NegativeIntCompanionJavaSample sample =
                new NegativeIntCompanionJavaSample();
        Assert.prints(expected, sample::createOrNull);
    }
}
