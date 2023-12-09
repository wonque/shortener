package io.liliac.shortener.validation;

public record ValidationError(
        ValidationErrorType type,
        String message
) {
}
