package io.liliac.shortener.validation.rules;


import io.liliac.shortener.validation.ValidationErrorType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InvalidUrlFormatValidationRuleTest {

    private final InvalidUrlFormatValidationRule rule = new InvalidUrlFormatValidationRule();

    @Test
    public void shouldReturnEmptyListIfInputIsValidUrl() {
        var validUrl = "http://valid.com";
        assertTrue(rule.apply(validUrl).isEmpty());
    }

    @Test
    public void shouldReturnListWithValidationErrorIfUrlFormatIsNotValid() {
        var invalidUrl = "not valid url";
        var errors = rule.apply(invalidUrl);
        assertEquals(1, errors.size());
        var error = errors.get(0);

        assertEquals(ValidationErrorType.INVALID_URL_FORMAT, error.type());
        assertFalse(error.message().isBlank());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void shouldReturnListWithValidationErrorIfUrlIsNullOrEmpty(String invalidUrl) {
        var errors = rule.apply(invalidUrl);
        assertEquals(1, errors.size());
        var error = errors.get(0);

        assertEquals(ValidationErrorType.INVALID_URL_FORMAT, error.type());
        assertFalse(error.message().isBlank());
    }
}