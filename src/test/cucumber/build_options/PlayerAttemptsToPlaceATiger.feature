Feature: Placing Tiger Conditions

  Scenario: When attempting to place a tiger on a volcano, the build fails
    Given a hex is of level three or greater
    When a player tries to place a tiger on a volcano
    Then the move is rejected

  Scenario: When attempting to place a tiger on hex of height less than three, the build fails
    Given a hex is not a volcano
    When a player tries to place a tiger on a hex of height three or less
    Then the move is rejected
