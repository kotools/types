package org.kotools.types;

class EmailAddressCompanionJavaSample {
    void patternSample() {
        final String pattern = EmailAddress.PATTERN;
        System.out.println(pattern); // ^\S+@\S+\.\S+$
    } // END

    void fromStringSample() {
        final Object value = "contact@kotools.org";
        try {
            EmailAddress.fromString(value); // TABS: 1
            System.out.println("success"); // TABS: 1
        } catch (final IllegalArgumentException exception) {
            System.out.println("failure"); // TABS: 1
        }
        // Output: success
    } // END

    void fromStringOrNull_Any() {
        final Object value = "contact@kotools.org";
        final EmailAddress address = EmailAddress.fromStringOrNull(value);
        System.out.println(address != null); // true
    } // END

    void fromStringOrNull_Any_Any_Sample() {
        final Object value = "contact@kotools.org";
        final Object pattern = "^[a-z]+@[a-z]+\\.[a-z]+$";
        final EmailAddress address =
                EmailAddress.fromStringOrNull(value, pattern); // TABS: 1
        System.out.println(address != null); // true
    } // END
}
