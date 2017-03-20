Feature: System Setup

  Scenario: Each Player Starts With Zero Points
  Given a game is created
    And that game has players
  Then all players have 0 points