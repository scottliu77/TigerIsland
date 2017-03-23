Feature: Game End Conditions

  Scenario: When a player has no more pieces left the game ends
    Given it is a player's turn
    When they play their last piece
    Then the game ends