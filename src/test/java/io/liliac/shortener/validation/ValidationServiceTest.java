package io.liliac.shortener.validation;

import io.liliac.shortener.dto.GenerateAliasRequest;
import io.liliac.shortener.validation.rules.ValidationRule;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ValidationServiceTest {

    private final ValidationRule ruleWithErrors = input -> List.of(new ValidationError(ValidationErrorType.INVALID_URL_FORMAT, "ruleWithErrors"));
    private final ValidationRule ruleWithNoErrors = input -> Collections.emptyList();
    private final ValidationRule anotherRuleWithError = input -> List.of(new ValidationError(ValidationErrorType.INVALID_URL_FORMAT, "anotherRuleWithError"));
    private final ValidationService service = new ValidationService(List.of(ruleWithErrors, ruleWithNoErrors, anotherRuleWithError));

    @Test
    public void validate_shouldReturnListWithAllErrorsGeneratedByAppliedRules() {
        var errors = service.validate(new GenerateAliasRequest("longUrl"));

        assertFalse(errors.isEmpty());
        assertEquals(2, errors.size());
    }
}