package io.liliac.shortener.validation;

import io.liliac.shortener.dto.ShortenUrlRequest;
import io.liliac.shortener.validation.rules.ValidationRule;
import io.quarkus.arc.All;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.*;

@ApplicationScoped
public class ValidationService {

    private List<ValidationRule> rules;

    @Inject
    public ValidationService(@All List<ValidationRule> rules) {
        this.rules = rules;
    }

    public List<ValidationError> validate(ShortenUrlRequest request) {
        return rules.stream().flatMap(rule -> rule.apply(request.url()).stream()).toList();
    }
}
