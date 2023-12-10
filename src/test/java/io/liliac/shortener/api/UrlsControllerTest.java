package io.liliac.shortener.api;

import io.liliac.shortener.dto.GenerateAliasResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
class UrlsControllerTest {

    @Test
    public void generateShortUrl_returnResponseWithAliasData() {
        var result = RestAssured.given()
                .body("{\"url\":\"https://en.wikipedia.org/wiki/Systems_design\"}")
                .header("Content-type", MediaType.APPLICATION_JSON)
                .when()
                .post("/urls/aliases")
                .then()
                .statusCode(200)
                .extract()
                .body().as(GenerateAliasResponse.class);

        assertNull(result.getValidationErrors());

        var aliasData = result.getAliasData();
        assertEquals("https://en.wikipedia.org/wiki/Systems_design", aliasData.source());
        assertFalse(aliasData.aliasFullUrl().isBlank());
        assertFalse(aliasData.domain().isBlank());
        assertFalse(aliasData.alias().isBlank());
        assertNotNull(aliasData.createdAt());
    }

    @Test
    public void generateShortUrl_returnResponseWithValidationErrorsIfGivenUrlNotValid() {
        var result = RestAssured.given()
                .body("{\"url\":\"not valid url\"}")
                .header("Content-type", MediaType.APPLICATION_JSON)
                .when()
                .post("/urls/aliases")
                .then()
                .statusCode(200)
                .extract()
                .body().as(GenerateAliasResponse.class);

        assertNotNull(result.getValidationErrors());
        assertNull(result.getAliasData());
        assertFalse(result.getValidationErrors().isEmpty());
    }
}