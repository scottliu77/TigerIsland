Feature: Placing Tiger Conditions

  Scenario: When attempting to place a tiger on an occupied hex, the build fails
    Given an occupied hex
    When a player tries to place a tiger on a hex
    Then the move is rejected

  Scenario: When attempting to place a tiger on a volcano, the build fails
    Given a hex is of level three or greater
    When a player tries to place a tiger on a hex
    Then the move is rejected

  Scenario: When attempting to place a tiger on hex of height less than three, the build fails
    Given a hex is not a volcano
    When a player tries to place a tiger on a hex
    Then the move is rejected
