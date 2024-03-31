package org.kotools.types;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailAddressCompanionJavaSampleTest {
    @Test
    void patternSample_should_pass() {
        final EmailAddressCompanionJavaSample sample =
                new EmailAddressCompanionJavaSample();
        final String actual = Assertions.assertDoesNotThrow(
                () -> SystemLambda.tapSystemOut(sample::patternSample).trim()
        );
        final String expected = "^\\S+@\\S+\\.\\S+$";
        Assertions.assertEquals(expected, actual);
    }
}
