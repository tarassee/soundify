package com.tarasiuk.soundify.component.resourceservice;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class GetAudioById {

    private static final String BASE_URL = "http://localhost:8080";
    private int audioId;
    private Response response;
    private byte[] audioContent;

    @Given("an audio file {string} is uploaded")
    public void anAudioFileIsUploaded(String audioFile) throws IOException {
        File file = new File(getClass().getClassLoader().getResource(audioFile).getFile());
        byte[] fileContent = Files.readAllBytes(file.toPath());
        audioContent = fileContent;

        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .multiPart("audio", file.getName(), fileContent, "audio/mpeg")
                .when()
                .post("/resources");

        audioId = response.body().jsonPath().get("id");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @When("a GET request is made to resource service")
    public void aGetRequestIsMadeTo() {
        this.response = RestAssured.given()
                .baseUri(BASE_URL)
                .when()
                .get("/resources/" + audioId);
    }

    @When("a GET request with non exists ID is made to resource service")
    public void aGETRequestWithNonExistsIDIsMadeToResourceService() {
        this.response = RestAssured.given()
                .baseUri(BASE_URL)
                .when()
                .get("/resources/" + 13255);
    }

    @Then("the response status code is {int}")
    public void theResponseStatusCodeIs(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @Then("the response body is the audio file")
    public void theResponseBodyIsTheAudioFile() {
        assertArrayEquals(audioContent, response.getBody().asByteArray());
    }

    @Then("the response contains an error message")
    public void theResponseContainsAnErrorMessage() {
        assertFalse(response.getBody().asString().isEmpty());
    }

}
