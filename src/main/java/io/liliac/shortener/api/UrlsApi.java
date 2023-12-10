package io.liliac.shortener.api;

import io.liliac.shortener.dto.GenerateAliasRequest;
import io.liliac.shortener.dto.GenerateAliasResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/urls")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface UrlsApi {

    @POST
    @Path("/aliases")
    GenerateAliasResponse generateAlias(GenerateAliasRequest request);
}
