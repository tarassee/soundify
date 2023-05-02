package com.tarasiuk.soundify.component.songservice;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UploadSongDefinitions {

    private static final String BASE_URL = "http://localhost:8085";
    private static Map<String, Object> songPayload;
    private Response response;

    @Given("a song data payload")
    public void aSongDataPayload() {
        songPayload = generateSongDataPayload();
    }

    @When("I upload the song data payload to the API")
    public void iUploadTheSongDataPayloadToTheApi() {
        this.response = RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(songPayload)
                .when()
                .post("/songs");
    }

    @Then("the API should respond with status code {int}")
    public void theApiShouldRespondWithStatusCode(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @And("the response should contain a JSON object with key {string} and a numeric value")
    public void theResponseShouldContainJsonObjectWithKeyAndNumericValue(String key) {
        var responseBody = response.getBody().as(Map.class);
        assertTrue(responseBody.containsKey(key));
        assertTrue(responseBody.get(key) instanceof Number);
    }

    @Given("an existing song in the database")
    public void anExistingSongInTheDatabase() {
        response = RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(songPayload)
                .when()
                .get("/songs/" + response.getBody().asString());
        assertEquals(200, response.getStatusCode());
    }

    @When("I upload the same song data payload to the API")
    public void iUploadTheSameSongDataPayloadToTheApi() {
        response = RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(songPayload)
                .when()
                .post("/songs");
    }

    @And("the response should contain an error message")
    public void theResponseShouldContainAnErrorMessage() {
        assertFalse(response.getBody().asString().isEmpty());
    }


    private static Map<String, Object> generateSongDataPayload() {
        var payload = new HashMap<String, Object>();
        payload.put("name", RandomStringUtils.randomAlphabetic(10));
        payload.put("artist", RandomStringUtils.randomAlphabetic(10));
        payload.put("resourceId", RandomStringUtils.randomNumeric(3));
        payload.put("album", RandomStringUtils.randomAlphabetic(10));
        payload.put("length", RandomStringUtils.randomNumeric(2) + ":" + RandomStringUtils.randomNumeric(2));
        payload.put("year", "20" + RandomStringUtils.randomNumeric(2));
        return payload;
    }

    @Given("an invalid song data payload")
    public void anInvalidSongDataPayload() {
        songPayload = Collections.emptyMap();
    }

}
