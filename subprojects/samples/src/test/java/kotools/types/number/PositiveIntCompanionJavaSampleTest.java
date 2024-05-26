package kotools.types.number;

import org.junit.jupiter.api.Test;
import org.kotools.types.Assert;

class PositiveIntCompanionJavaSampleTest {
    @Test
    void create_should_pass() {
        final String expected = "23";
        final PositiveIntCompanionJavaSample sample =
                new PositiveIntCompanionJavaSample();
        Assert.prints(expected, sample::create);
    }

    @Test
    void createOrNull_should_pass() {
        final String expected = "23";
        final PositiveIntCompanionJavaSample sample =
                new PositiveIntCompanionJavaSample();
        Assert.prints(expected, sample::createOrNull);
    }
}
