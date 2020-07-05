Feature: sample

  Scenario: Testing valid GET endpoint
    Given url 'http://localhost:8080/health'
    When method GET
    Then status 200
    And match $ == {status:'UP'}