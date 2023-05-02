Feature: Process Audio File

  Scenario: Successful audio processing between microservice
    Given an audio file "testdata/audio/Nirvana_Dumb.mp3" is uploaded via POST request to resource service
    When metadata is retrieved from song service by retrieved ID via GET request
    Then response status code is 200
    And response body is present

  Scenario: Failure audio processing between microservice because of empty metadata
    Given an audio file "testdata/audio/Wind_Arctic_No_Metadata.mp3" is uploaded via POST request to resource service
    When metadata is retrieved from song service by retrieved ID via GET request
    Then response status code is 404
