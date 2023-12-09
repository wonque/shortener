package io.liliac.shortener.validation.rules;

import io.liliac.shortener.validation.ValidationError;
import io.liliac.shortener.validation.ValidationErrorType;
import jakarta.enterprise.context.ApplicationScoped;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

@ApplicationScoped
public class InvalidUrlFormatValidationRule implements ValidationRule {

    @Override
    public List<ValidationError> apply(String input) {
        List<ValidationError> result = new ArrayList<>();
        try {
            new URL(input).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            ValidationError error = new ValidationError(
                    ValidationErrorType.INVALID_URL_FORMAT,
                    "Url must be formatted according to RFC2396"
            );
            result.add(error);
        }
        return result;
    }
}
