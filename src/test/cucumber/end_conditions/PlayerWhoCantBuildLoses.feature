Feature: Game End Conditions

  Scenario: When a player cannot make a valid move they lose
    Given it is a player's turn
    When that player is unable to build
    And they have not played their last piece
    Then then the next highest scoring player wins