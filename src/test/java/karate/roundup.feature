Feature: For a customer, take all the transactions in a given week and round them up to the
  nearest pound. For example with spending of £4.35, £5.20 and £0.87, the round-up
  would be £1.58. This amount should then be transferred into a savings goal, helping the
  customer save for future adventures

  Scenario: Roundup for a 6 month period
    Given url 'http://localhost:8080/api/v1/account/0f626e6b-d357-4a35-8219-c4e62df5f326/round-up/e5ed27ef-6bc5-45fd-adf8-2697c4cad02b?fromDate=2020-01-01T00:00:00.000Z&toDate=2020-07-01T00:00:00.000Z'
    When method PUT
    And header Authorization = call read('karate-config.js') { username: 'admin', password: 'admin' }
    Then status 200
    And match $ ==
    """
    {
      "accountUid": "0f626e6b-d357-4a35-8219-c4e62df5f326",
      "savingsGoalUid": "e5ed27ef-6bc5-45fd-adf8-2697c4cad02b",
      "transferUid": '#string',
      "roundedForPeriod": {
          "currency": "GBP",
          "minorUnits": 12333
      },
      "totalSaved": {
          "currency": "GBP",
          "minorUnits": 123456
      }
    }
    """

  Scenario: FromDate after ToDate
    Given url 'http://localhost:8080/api/v1/account/0f626e6b-d357-4a35-8219-c4e62df5f326/round-up/e5ed27ef-6bc5-45fd-adf8-2697c4cad02b?fromDate=2020-01-01T00:00:00.000Z&toDate=2019-07-01T00:00:00.000Z'
    When method PUT
    And header Authorization = call read('karate-config.js') { username: 'admin', password: 'admin' }
    Then status 400
    And match $ ==
    """
    {
        "timestamp": '#string',,
        "status": 400,
        "error": "Bad Request",
        "message": "Invalid Date Range",
        "path": "/api/v1/account/0f626e6b-d357-4a35-8219-c4e62df5f326/savings-goal/e5ed27ef-6bc5-45fd-adf8-2697c4cad02b/roundUp?fromDate=2020-01-01T00:00:00.000Z&toDateDate=2019-07-01T00:00:00.000Z"
    }
    """

  Scenario: Invalid Account Id
    Given url 'http://localhost:8080/api/v1/account/123/round-up/e5ed27ef-6bc5-45fd-adf8-2697c4cad02b?fromDate=2020-01-01T00:00:00.000Z&toDate=2019-07-01T00:00:00.000Z'
    When method PUT
    And header Authorization = call read('karate-config.js') { username: 'admin', password: 'admin' }
    Then status 400
    And match $ ==
    """
    {
        "timestamp": '#string',,
        "status": 400,
        "error": "Bad Request",
        "message": "Invalid AccountId",
        "path": "/api/v1/account/123/round-up/e5ed27ef-6bc5-45fd-adf8-2697c4cad02b?fromDate=2020-01-01T00:00:00.000Z&toDateDate=2019-07-01T00:00:00.000Z"
    }
    """

  Scenario: Invalid Savings Goal
    Given url 'http://localhost:8080/api/v1/account/0f626e6b-d357-4a35-8219-c4e62df5f326/round-up/1234?fromDate=2020-01-01T00:00:00.000Z&toDate=2019-07-01T00:00:00.000Z'
    When method PUT
    And header Authorization = call read('karate-config.js') { username: 'admin', password: 'admin' }
    Then status 400
    And match $ ==
    """
    {
        "timestamp": '#string',,
        "status": 400,
        "error": "Bad Request",
        "message": "Invalid Savings Goal",
        "path": "/api/v1/account/0f626e6b-d357-4a35-8219-c4e62df5f326/round-up/1234?fromDate=2020-01-01T00:00:00.000Z&toDateDate=2019-07-01T00:00:00.000Z"
    }
    """

  Scenario: Starling Api Gateway Timeout
    Given url 'http://localhost:8080/api/v1/account/0f626e6b-d357-4a35-8219-c4e62df5f326/round-up/1234?fromDate=2020-01-01T00:00:00.000Z&toDate=2019-07-01T00:00:00.000Z'
    When method PUT
    And header Authorization = call read('karate-config.js') { username: 'admin', password: 'admin' }
    Then status 400
    And match $ ==
    """
    {
        "timestamp": '#string',,
        "status": 500,
        "error": "Internal Server Error",
        "message": "Error processing the request",
        "path": "/api/v1/account/0f626e6b-d357-4a35-8219-c4e62df5f326/round-up/1234?fromDate=2020-01-01T00:00:00.000Z&toDateDate=2019-07-01T00:00:00.000Z"
    }
    """

  Scenario: Invalid Basic Auth
    Given url 'http://localhost:8080/api/v1/account/0f626e6b-d357-4a35-8219-c4e62df5f326/round-up/1234?fromDate=2020-01-01T00:00:00.000Z&toDate=2019-07-01T00:00:00.000Z'
    When method PUT
    Then status 401
    And match $ ==
    """
    {
        "timestamp": '#string',,
        "status": 401,
        "error": "Unauthorized",
        "message": "Error processing the request",
        "path": "/api/v1/account/0f626e6b-d357-4a35-8219-c4e62df5f326/round-up/1234?fromDate=2020-01-01T00:00:00.000Z&toDateDate=2019-07-01T00:00:00.000Z"
    }
    """
