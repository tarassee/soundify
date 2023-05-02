Feature: Get Audio By Id

  Background:
    Given an audio file "testdata/audio/Nirvana_Dumb.mp3" is uploaded

  Scenario: Successfully retrieve audio file by ID
    When a GET request is made to resource service
    Then the response status code is 200
    And the response body is the audio file

  Scenario: Retrieve audio file by non existent ID
    When a GET request with non exists ID is made to resource service
    Then the response status code is 404
    And the response contains an error message
