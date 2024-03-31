package org.kotools.types;

class EmailAddressCompanionJavaSample {
    void patternSample() {
        final String pattern = EmailAddress.PATTERN;
        System.out.println(pattern); // ^\S+@\S+\.\S+$
    }

    void fromStringOrNullSample() {
        final Object value = "contact@kotools.org";
        final EmailAddress address = EmailAddress.fromStringOrNull(value);
        System.out.println(address != null); // true
    } // END
}
