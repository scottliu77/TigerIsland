Feature: Game End Conditions

  Scenario: When a tiebreaker is needed, and players have same number of totoros and tigers, the player with fewer villagers remaining wins
    Given it is a player's turn
    When there are no unplayed tiles left
    And the scores are tied
    And the number of totoros are tied
    And the number of tigers are tied
    Then the player with the fewest remaining villagers wins