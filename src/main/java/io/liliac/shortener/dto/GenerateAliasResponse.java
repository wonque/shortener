package io.liliac.shortener.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.liliac.shortener.validation.ValidationError;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class GenerateAliasResponse {
    private List<ValidationError> validationErrors;
    @JsonProperty("data")
    private AliasDataDto aliasData;

    public GenerateAliasResponse() {
    }

    public GenerateAliasResponse(AliasDataDto aliasData) {
        this.aliasData = aliasData;
    }

    public GenerateAliasResponse(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public AliasDataDto getAliasData() {
        return aliasData;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }
}
