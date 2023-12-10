package io.liliac.shortener.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/")
public interface AliasesApi {

    @GET
    @Path("{alias}")
    Response redirectToSource(@PathParam("alias") String alias);
}
