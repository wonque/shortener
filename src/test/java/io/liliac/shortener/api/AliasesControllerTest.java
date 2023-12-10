package io.liliac.shortener.api;

import io.liliac.shortener.dto.GenerateAliasResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.config.RedirectConfig.redirectConfig;

@QuarkusTest
public class AliasesControllerTest {

    @Test
    public void redirectToLongUrl_createShortUrlRecordAndRequestRedirectLocation() {
        var createResult = RestAssured.given()
                .body("{\"url\":\"https://en.wikipedia.org/wiki/Systems_design\"}")
                .header("Content-type", MediaType.APPLICATION_JSON)
                .when()
                .post("/urls/aliases")
                .then()
                .statusCode(200)
                .extract()
                .body().as(GenerateAliasResponse.class);

        RestAssured.given()
                .config(RestAssured
                        .config()
                        .redirect(redirectConfig()
                                .followRedirects(false)))
                .when()
                .get(createResult.getAliasData().alias())
                .then()
                .statusCode(Response.Status.MOVED_PERMANENTLY.getStatusCode())
                .header("Location", Matchers.equalTo("https://en.wikipedia.org/wiki/Systems_design"));
    }

    @Test
    public void redirectToLongUrl_notFoundIfNoSuchShortUrlExists() {
        RestAssured.given()
                .when()
                .get("random")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}
