package io.liliac.shortener.api;

import io.liliac.shortener.dto.ShortenUrlRequest;
import io.liliac.shortener.dto.ShortenUrlResponse;
import io.liliac.shortener.exception.MappingNotFoundException;
import io.liliac.shortener.service.RedirectService;
import io.liliac.shortener.service.ShortenUrlService;
import io.liliac.shortener.validation.ValidationError;
import io.liliac.shortener.validation.ValidationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.*;

public class UrlController implements UrlApi {

    private final Logger logger = LoggerFactory.getLogger(UrlController.class);

    private ShortenUrlService shortenUrlService;
    private RedirectService redirectService;
    private ValidationService validationService;

    @Inject
    public UrlController(ShortenUrlService shortenUrlService,
                         RedirectService redirectService,
                         ValidationService validationService) {
        this.shortenUrlService = shortenUrlService;
        this.redirectService = redirectService;
        this.validationService = validationService;
    }

    @Override
    public ShortenUrlResponse shorten(ShortenUrlRequest request) {
        List<ValidationError> validationErrors = validationService.validate(request);
        return validationErrors.isEmpty()
                ? shortenUrlService.process(request.url())
                : new ShortenUrlResponse(validationErrors);
    }

    @Override
    public Response redirect(String shortUrl) {
        logger.debug("Redirect request to {}", shortUrl);
        if (shortUrl.isBlank()) {
            throw new BadRequestException("Non-blank url expected");
        }
        try {
            String location = redirectService.getLocation(shortUrl);
            return Response.status(Response.Status.MOVED_PERMANENTLY).location(URI.create(location)).build();
        } catch (MappingNotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }
}
