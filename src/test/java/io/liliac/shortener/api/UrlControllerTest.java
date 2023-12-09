package io.liliac.shortener.api;

import io.liliac.shortener.dto.ShortenUrlResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class UrlControllerTest {

    @Test
    public void shorten_returnResponseWithShortenUrlAndSourceUrl() {
        var result = RestAssured.given()
                .body("{\"url\":\"https://en.wikipedia.org/wiki/Systems_design\"}")
                .header("Content-type", MediaType.APPLICATION_JSON)
                .when()
                .post("/url/shorten")
                .then()
                .statusCode(200)
                .extract()
                .body().as(ShortenUrlResponse.class);

        assertFalse(result.getShortUrl().isBlank());
        assertTrue(result.getValidationErrors().isEmpty());
        assertEquals("https://en.wikipedia.org/wiki/Systems_design", result.getSource());
    }

    @Test
    public void shorten_returnResponseWithValidationErrorsIfGivenUrlNotValid() {
        var result = RestAssured.given()
                .body("{\"url\":\"not valid url\"}")
                .header("Content-type", MediaType.APPLICATION_JSON)
                .when()
                .post("/url/shorten")
                .then()
                .statusCode(200)
                .extract()
                .body().as(ShortenUrlResponse.class);

        assertNull(result.getShortUrl());
        assertNull(result.getSource());
        assertFalse(result.getValidationErrors().isEmpty());
    }

    @Test
    public void redirect_createShortUrlRecordAndRequestRedirectLocation() {
        var createResult = RestAssured.given()
                .body("{\"url\":\"https://en.wikipedia.org/wiki/Systems_design\"}")
                .header("Content-type", MediaType.APPLICATION_JSON)
                .when()
                .post("/url/shorten")
                .then()
                .statusCode(200)
                .extract()
                .body().as(ShortenUrlResponse.class);

        RestAssured.given()
                .config(RestAssured
                        .config()
                        .redirect(redirectConfig()
                        .followRedirects(false)))
                .when()
                .get(createResult.getShortUrl())
                .then()
                .statusCode(Response.Status.MOVED_PERMANENTLY.getStatusCode())
                .header("Location", Matchers.equalTo("https://en.wikipedia.org/wiki/Systems_design"));
    }

    @Test
    public void redirect_notFoundIfNoSuchShortUrlExists() {
        RestAssured.given()
                .when()
                .get("random")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}