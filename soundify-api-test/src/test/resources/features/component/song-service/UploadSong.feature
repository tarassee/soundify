Feature: Upload Song

  Scenario: Upload a new song
    Given a song data payload
    When I upload the song data payload to the API
    Then the API should respond with status code 200
    And the response should contain a JSON object with key "id" and a numeric value
    Given I upload the same song data payload to the API
    Then the API should respond with status code 400
    And the response should contain an error message

  Scenario: Upload an invalid song
    Given an invalid song data payload
    When I upload the song data payload to the API
    Then the API should respond with status code 400
    And the response should contain an error message
