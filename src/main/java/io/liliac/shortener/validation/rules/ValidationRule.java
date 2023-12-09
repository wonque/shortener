package io.liliac.shortener.validation.rules;

import io.liliac.shortener.validation.ValidationError;

import java.util.*;

public interface ValidationRule {

    List<ValidationError> apply(String input);
}
