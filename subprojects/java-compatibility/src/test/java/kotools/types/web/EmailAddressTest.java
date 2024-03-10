package kotools.types.web;

import kotools.types.internal.ErrorMessage;
import kotools.types.internal.ErrorMessageKt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class EmailAddressTest {
    private static final String TEXT_SAMPLE = "contact@kotools.org";
    private static final Set<String> VALID_TEXTS =
            Set.of("contact@kotools.org", "cont.act@kotools.org");
    private static final Set<String> INVALID_TEXTS = Set.of(
            " contact@kotools.org",
            "cont-act@kotools.org",
            "contact-kotools.org",
            "contact@ko tools. org",
            "contact@kotools_org"
    );

    @Nested
    public class Companion {
        @Test
        public void create_should_pass_with_a_valid_String() {
            VALID_TEXTS.forEach(EmailAddress.Companion::create);
        }

        @Test
        public void create_should_fail_with_an_invalid_String() {
            INVALID_TEXTS.forEach(text -> {
                final IllegalArgumentException exception =
                        Assertions.assertThrows(
                                IllegalArgumentException.class,
                                () -> EmailAddress.Companion.create(text)
                        );
                final ErrorMessage actual =
                        ErrorMessageKt.ErrorMessage(exception);
                final ErrorMessage expected =
                        ErrorMessage.Companion.invalidEmailAddress(text);
                Assertions.assertEquals(expected, actual);
            });
        }

        @Test
        public void createOrNull_should_pass() {
            final EmailAddress actual =
                    EmailAddress.Companion.createOrNull(TEXT_SAMPLE);
            Assertions.assertNotNull(actual);
        }
    }

    @Test
    public void equals_should_pass() {
        final EmailAddress first =
                EmailAddress.Companion.createOrNull(TEXT_SAMPLE);
        Assertions.assertNotNull(first);
        final EmailAddress second =
                EmailAddress.Companion.createOrNull(TEXT_SAMPLE);
        Assertions.assertNotNull(second);
        Assertions.assertEquals(first, second);
    }

    @Test
    public void hashCode_should_pass() {
        final EmailAddress first =
                EmailAddress.Companion.createOrNull(TEXT_SAMPLE);
        Assertions.assertNotNull(first);
        final EmailAddress second =
                EmailAddress.Companion.createOrNull(TEXT_SAMPLE);
        Assertions.assertNotNull(second);
        final int firstHashCode = first.hashCode();
        final int secondHashCode = second.hashCode();
        Assertions.assertEquals(firstHashCode, secondHashCode);
    }

    @Test
    public void toString_should_pass() {
        final String expected = TEXT_SAMPLE;
        final EmailAddress address =
                EmailAddress.Companion.createOrNull(expected);
        Assertions.assertNotNull(address);
        final String actual = address.toString();
        Assertions.assertEquals(expected, actual);
    }
}
