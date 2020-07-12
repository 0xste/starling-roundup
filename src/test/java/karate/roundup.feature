Feature: For a customer, take all the transactions in a given week and round them up to the
  nearest pound. For example with spending of £4.35, £5.20 and £0.87, the round-up
  would be £1.58. This amount should then be transferred into a savings goal, helping the
  customer save for future adventures

  Scenario: Roundup for a 6 month period
    Given url 'http://localhost:8080/api/v1/account/0f626e6b-d357-4a35-8219-c4e62df5f326/round-up/e5ed27ef-6bc5-45fd-adf8-2697c4cad02b?fromDate=2020-01-01T00:00:00.000Z&toDate=2020-07-01T00:00:00.000Z'
    When method PUT
    Then status 200
    And match $ ==
    """
    {
        "savingsGoalUid": "e5ed27ef-6bc5-45fd-adf8-2697c4cad02b",
        "accountId": "0f626e6b-d357-4a35-8219-c4e62df5f326",
        "transferUid": "acea7b59-e92c-40c4-a7d1-5c2497445df3",
        "totalAdded": {
            "currency": "GBP",
            "minorUnits": 712
        }
    }
    """