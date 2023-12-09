package io.liliac.shortener.validation;

import io.liliac.shortener.dto.ShortenUrlRequest;
import io.liliac.shortener.validation.rules.ValidationRule;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ValidationServiceTest {

    private ValidationRule ruleWithErrors = input -> List.of(new ValidationError(ValidationErrorType.INVALID_URL_FORMAT, "ruleWithErrors"));
    private ValidationRule ruleWithNoErrors = input -> Collections.emptyList();
    private ValidationRule anotherRuleWithError = input -> List.of(new ValidationError(ValidationErrorType.INVALID_URL_FORMAT, "anotherRuleWithError"));
    private ValidationService service = new ValidationService(List.of(ruleWithErrors, ruleWithNoErrors, anotherRuleWithError));

    @Test
    public void validate_shouldReturnListWithAllErrorsGeneratedByAppliedRules() {
        var errors = service.validate(new ShortenUrlRequest("longUrl"));

        assertFalse(errors.isEmpty());
        assertEquals(2, errors.size());
    }
}