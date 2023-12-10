package io.liliac.shortener.api;

import io.liliac.shortener.exception.MappingNotFoundException;
import io.liliac.shortener.service.RedirectService;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URI;

public class AliasesController implements AliasesApi {

    private final Logger logger = LoggerFactory.getLogger(AliasesController.class);

    private RedirectService redirectService;

    @Inject
    AliasesController(RedirectService redirectService) {
        this.redirectService = redirectService;
    }

    @Override
    public Response redirectToSource(String alias) {
        if (alias.isBlank()) {
            throw new BadRequestException("Non-blank url expected");
        }
        try {
            String location = redirectService.getRedirectSource(alias);
            return Response.status(Response.Status.MOVED_PERMANENTLY).location(URI.create(location)).build();
        } catch (MappingNotFoundException ex) {
            logger.debug(ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }
}
