Feature: System Setup

  Scenario: Each Player starts with 3 Totoros
    Given a game is created
      And that game has players
    Then all players have 3 totoros