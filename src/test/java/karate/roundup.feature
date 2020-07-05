Feature: sample

  Scenario: Testing valid GET endpoint
    Given url 'http://localhost:8080/roundup'
    When method POST
    Then status 200
    And match $ == {status:'UP'}