package com.tarasiuk.soundify.e2e;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AudioProcessingFlowDefinitions {

    private static final String SONG_SERVICE_BASE_URL = "http://localhost:8085";
    private static final String RESOURCE_SERVICE_BASE_URL = "http://localhost:8080";
    private int audioId;
    private Response response;

    @Given("an audio file {string} is uploaded via POST request to resource service")
    public void anAudioFileIsUploadedViaPOSTRequestToResourceService(String audioFile) throws IOException {
        File file = new File(getClass().getClassLoader().getResource(audioFile).getFile());
        byte[] fileContent = Files.readAllBytes(file.toPath());

        Response response = RestAssured.given()
                .baseUri(RESOURCE_SERVICE_BASE_URL)
                .multiPart("audio", file.getName(), fileContent, "audio/mpeg")
                .when()
                .post("/resources");

        audioId = response.body().jsonPath().get("id");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @When("metadata is retrieved from song service by retrieved ID via GET request")
    public void metadataCanBeRetrievedFromSongServiceByRetrievedIDViaGetRequest() throws InterruptedException {
        Thread.sleep(5000);
        response = RestAssured.given()
                .baseUri(SONG_SERVICE_BASE_URL)
                .contentType(ContentType.JSON)
                .when()
                .get("/songs/resource/" + audioId);
    }

    @And("response body is present")
    public void responseBodyIsPresent() {
        assertFalse(response.getBody().asString().isEmpty());
    }

    @Then("response status code is {int}")
    public void responseStatusCodeIs(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

}
