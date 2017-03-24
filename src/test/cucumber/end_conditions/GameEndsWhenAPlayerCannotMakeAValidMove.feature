Feature: Game End Conditions

  Scenario: When a player has no more pieces left the game ends
    Given it is a player's turn
    When that player is unable to build
      And they have not played their last piece
    Then the game ends