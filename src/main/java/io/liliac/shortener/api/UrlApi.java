package io.liliac.shortener.api;

import io.liliac.shortener.dto.ShortenUrlRequest;
import io.liliac.shortener.dto.ShortenUrlResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("")
public interface UrlApi {

    @POST
    @Path("/url/shorten")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    ShortenUrlResponse shorten(ShortenUrlRequest request);

    @GET
    @Path("{shortUrl}")
    Response redirect(@PathParam("shortUrl") String shortUrl);
}
