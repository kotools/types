package org.kotools.types;

class EmailAddressCompanionJavaSample {
    void patternSample() {
        final String pattern = EmailAddress.PATTERN;
        System.out.println(pattern); // ^\S+@\S+\.\S+$
    } // END

    void fromString_Any() {
        final Object value = "contact@kotools.org";
        try {
            EmailAddress.fromString(value);
            System.out.println("success");
        } catch (final IllegalArgumentException exception) {
            System.out.println("failure");
        }
        // Output: success
    } // END

    void fromString_Any_Any() {
        final Object value = "contact@kotools.org";
        final Object pattern = "^[a-z]+@[a-z]+\\.[a-z]+$";
        try {
            EmailAddress.fromString(value, pattern);
            System.out.println("success");
        } catch (final IllegalArgumentException exception) {
            System.out.println("failure");
        }
        // Output: success
    } // END

    void fromStringOrNull_Any() {
        final Object value = "contact@kotools.org";
        final EmailAddress address = EmailAddress.fromStringOrNull(value);
        System.out.println(address != null); // true
    } // END

    void fromStringOrNull_Any_Any() {
        final Object value = "contact@kotools.org";
        final Object pattern = "^[a-z]+@[a-z]+\\.[a-z]+$";
        final EmailAddress address =
                EmailAddress.fromStringOrNull(value, pattern);
        System.out.println(address != null); // true
    } // END

    void orNull_Any() {
        final Object value = "contact@kotools.org";
        final EmailAddress address = EmailAddress.orNull(value);
        System.out.println(address != null); // true
    } // END
}
