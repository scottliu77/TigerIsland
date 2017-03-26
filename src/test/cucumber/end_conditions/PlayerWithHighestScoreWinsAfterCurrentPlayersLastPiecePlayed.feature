Feature: Game End Conditions

  Scenario: Player with highest score wins after current players last piece played
    Given it is a player's turn
    When they play their last piece
    And only one player has the top score
    Then then the player with the highest score wins