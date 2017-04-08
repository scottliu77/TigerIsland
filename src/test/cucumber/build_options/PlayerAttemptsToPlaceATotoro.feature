Feature: Placing Totoro Conditions



  Scenario: When attempting to place a totoro on a small settlement, the build fails
    Given a settlement too small to accept a totoro
    When a player tries to place a totoro in the settlement
    Then the move is rejected

  Scenario: When attempting to place a totoro on a settlement with a totoro in it, the build fails
    Given a settlement already containing a totoro
    When a player tries to place a totoro in the settlement
    Then the move is rejected

  Scenario: When attempting to place a totoro on an occupied hex in a settlement, the build fails
    Given a settlement capable of accepting a totoro
    When a player tries to place a totoro on an occupied hex of the settlement
    Then the move is rejected

  Scenario: When attempting to place a tororo on an unplaced hex in a settlement, the build fails
    Given a settlement capable of accepting a totoro with no adjacent placed hex
    When a player tries to place a totoro on an unplaced hex adjacent to the settlement
    Then the move is rejected

  Scenario: When attempting to place a tororo on a volcano, the build fails
    Given a settlement capable of accepting a totoro
    When a player tries to place a totoro on a volcano
    Then the move is rejected

  Scenario: When attempting to place a totoro in a location that bridges the gap between two small settlements, the build fails
    Given a settlement too small to accept a totoro
    And another settlement too small to accept a totoro
    When a player tries to place a totoro on the hex bridging the gap
    Then the move is rejected

  Scenario: When attempting to place a totoro in a location that bridges the gap between a settlement containing a totoro and a valid settlement to place it in, the build succeeds
    Given a settlement capable of accepting a totoro
    And a nearby settlement containing a totoro
    When a player tries to place a totoro on the hex bridging the gap
    Then the move is accepted


  Scenario: When attempting to place a totoro on a valid hex in a valid settlement, the build succeeds
    Given a settlement capable of accepting a totoro
    When a player tries to place a totoro validly
    Then the move is accepted
    And the player's inventory updates properly

  Scenario: When attempting to place a totoro in a settlement already containing a tiger, the build succeeds
    Given a settlement containing a tiger and is valid to build a totoro
    When a player tries to place a totoro in the settlement
    Then the move is accepted

  Scenario: When attempting to place a totoro directly next to a tiger in a settlement, the build succeeds
    Given a settlement containing a tiger and a valid hex directly next to the tiger
    When a player tries to place a totoro in the settlement
    Then the move is accepted

  Scenario: When attempting to place a totoro in a valid settlement on a hex with height greater than one, the build succeeds
    Given a settlement capable of accepting a totoro with a valid hex of height greater than one
    When a player tries to place a totoro in the settlement
    Then the move is accepted

