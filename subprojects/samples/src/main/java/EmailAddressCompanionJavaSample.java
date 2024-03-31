package org.kotools.types;

class EmailAddressCompanionJavaSample {
    void patternSample() {
        final String pattern = EmailAddress.PATTERN;
        System.out.println(pattern); // ^\S+@\S+\.\S+$
    }
}
