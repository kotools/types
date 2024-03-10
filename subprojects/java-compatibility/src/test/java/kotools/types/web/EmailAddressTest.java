package kotools.types.web;

import kotools.types.internal.ErrorMessage;
import kotools.types.internal.ErrorMessageKt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Set;

public class EmailAddressTest {
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
        public void createOrNull_should_pass_with_a_valid_String() {
            VALID_TEXTS.forEach(text -> {
                final EmailAddress actual =
                        EmailAddress.Companion.createOrNull(text);
                Assertions.assertNotNull(actual);
            });
        }

        @Test
        public void createOrNull_should_fail_with_an_invalid_String() {
            INVALID_TEXTS.forEach(text -> {
                final EmailAddress actual =
                        EmailAddress.Companion.createOrNull(text);
                Assertions.assertNull(actual);
            });
        }
    }

    @Test
    public void structural_equality_should_pass_with_another_EmailAddress_having_the_same_string_representation() {
        final Object[] validTexts = VALID_TEXTS.toArray();
        final int index = new Random()
                .nextInt(validTexts.length);
        final String text = validTexts[index].toString();
        final EmailAddress first = EmailAddress.Companion.create(text);
        final EmailAddress second = EmailAddress.Companion.create(text);
        Assertions.assertEquals(first, second);
        final int hashCode1 = first.hashCode();
        final int hashCode2 = second.hashCode();
        Assertions.assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void structural_equality_should_fail_with_another_EmailAddress_having_another_string_representation() {
        final Object[] validTexts = VALID_TEXTS.toArray();
        final int index = new Random()
                .nextInt(validTexts.length);
        final String text1 = validTexts[index].toString();
        final EmailAddress first = EmailAddress.Companion.create(text1);
        final String text2 = "support.%s".formatted(text1);
        final EmailAddress second = EmailAddress.Companion.create(text2);
        Assertions.assertNotEquals(first, second);
        final int hashCode1 = first.hashCode();
        final int hashCode2 = second.hashCode();
        Assertions.assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    public void toString_should_pass() {
        final Object[] validTexts = VALID_TEXTS.toArray();
        final int index = new Random()
                .nextInt(validTexts.length);
        final String text = validTexts[index].toString();
        final EmailAddress address =
                EmailAddress.Companion.createOrNull(text);
        Assertions.assertNotNull(address);
        final String actual = address.toString();
        Assertions.assertEquals(text, actual);
    }
}
