Feature: Game End Conditions

  Scenario: When a tiebreaker is needed, the player with fewer Totoros remaining wins
    Given it is a player's turn
    When there are no unplayed tiles left
      And the scores are tied
    Then the player with the fewest remaining totoros wins