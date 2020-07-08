Feature: sample

  Scenario: Testing valid GET endpoint
    Given url 'http://localhost:8080/health'
    When method GET
    And header Authorization = call read('karate-config.js') { username: 'admin', password: 'admin' }
    Then status 200
    And match $ == {status:'UP'}