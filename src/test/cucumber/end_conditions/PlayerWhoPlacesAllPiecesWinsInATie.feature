Feature: Game End Conditions

  Scenario: When a player places all their pieces and their is a tie, they win
    Given it is a player's turn
    When they play their last piece
      And the top scoring players have the same score
    Then then the player who placed all their pieces wins