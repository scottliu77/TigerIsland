Feature: Placing Tiger Conditions

  Scenario: When attempting to place a tiger on an occupied hex, the build fails
    Given an occupied hex
    When a player tries to place a tiger on a hex
    Then the move is rejected

  Scenario: When attempting to place a tiger on a volcano, the build fails
    Given a volcano hex is of level three or greater
    When a player tries to place a tiger on a hex
    Then the move is rejected

  Scenario: When attempting to place a tiger on hex of height less than three, the build fails
    Given a hex is not a volcano
    When a player tries to place a tiger on a hex
    Then the move is rejected

  Scenario: When attempting to place a tiger on a settlement with a tiger in it, the build fails
    Given a settlement already containing a tiger
    When a player tries to place a tiger in the settlement
    Then the move is rejected

  Scenario: When attempting to place a tiger on a hex not part of a settlement, the build fails
    Given a hex is not part of a settlement
    When a player tries to place a tiger on a hex
    Then the move is rejected

  Scenario: When attempting to place a tiger when a player is out of tiger pieces, the build fails
    Given a settlement that can build a tiger but the player is out of tiger pieces
    When a player tries to place a tiger on a hex
    Then the move is rejected

  Scenario: When attempting to place a tiger on a valid hex at level 3, the build passes
    Given a settlement adjacent to a level three valid hex
    When a player tries to place a tiger on a hex
    Then the move is accepted

  Scenario: When attempting to place a tiger on a hex with height greater than 3, the build passes
    Given a settlement adjacent to a level four valid hex
    When a player tries to place a tiger on a hex
    Then the move is accepted

  Scenario: When attempting to place a tiger in a settlement with a totoro already, the build passes
    Given a settlement containing a totoro and a valid hex for tiger placement
    When a player tries to place a tiger on a hex
    Then the move is accepted

  Scenario: When attempting to place a tiger directly next to a totoro in a settlement, the build passes
    Given a settlement containing a totoro and a valid hex directly next to a totoro
    When a player tries to place a tiger on a hex
    Then the move is accepted