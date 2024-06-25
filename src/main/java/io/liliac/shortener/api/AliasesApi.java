package io.liliac.shortener.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

@Path("/")
public interface AliasesApi {

    @GET
    @Path("{alias}")
    Response redirectToSource(@RestPath("alias") String alias);
}
