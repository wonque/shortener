package io.liliac.shortener.api;

import io.liliac.shortener.dto.GenerateAliasRequest;
import io.liliac.shortener.dto.GenerateAliasResponse;
import io.liliac.shortener.service.UrlAliasService;
import io.liliac.shortener.validation.ValidationError;
import io.liliac.shortener.validation.ValidationService;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class UrlsController implements UrlsApi {

    private final Logger logger = LoggerFactory.getLogger(UrlsController.class);

    private final UrlAliasService urlAliasService;
    private final ValidationService validationService;

    @Inject
    UrlsController(UrlAliasService urlAliasService,
                          ValidationService validationService) {
        this.urlAliasService = urlAliasService;
        this.validationService = validationService;
    }

    @Override
    public GenerateAliasResponse generateAlias(GenerateAliasRequest request) {
        List<ValidationError> validationErrors = validationService.validate(request);
        if(validationErrors.isEmpty()) {
            return new GenerateAliasResponse(urlAliasService.createAlias(request.url()));
        }
        logger.debug("Generate alias request validation failed with validation errors: {}", validationErrors);
        return new GenerateAliasResponse(validationErrors);
    }
}
