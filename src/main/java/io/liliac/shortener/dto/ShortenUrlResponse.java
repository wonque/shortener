package io.liliac.shortener.dto;

import io.liliac.shortener.validation.ValidationError;

import java.util.*;

public final class ShortenUrlResponse {
    private String shortUrl;
    private String source;
    private List<ValidationError> validationErrors;

    public ShortenUrlResponse() {
    }

    public ShortenUrlResponse(String shortUrl, String source) {
        this.shortUrl = shortUrl;
        this.source = source;
        this.validationErrors = Collections.emptyList();
    }

    public ShortenUrlResponse(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getSource() {
        return source;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
