Feature: Game End Conditions

  Scenario: When a tiebreaker is needed, and players have same number of totoros, the player with fewer Tigers remaining wins
    Given it is a player's turn
    When there are no unplayed tiles left
      And the scores are tied
      And the number of totoros are tied
    Then the player with the fewest remaining tigers wins