Feature: System Setup

  Scenario: Each Player starts with 3 Totoros
    Given a game is created
      And that game has players
    When the game has not yet started
    Then all players have 3 totoros