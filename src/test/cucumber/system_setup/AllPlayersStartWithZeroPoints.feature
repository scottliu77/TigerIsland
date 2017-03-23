Feature: System Setup

  Scenario: Each Player Starts With Zero Points
  Given a game is created
    And that game has players
  When the game has not yet started
  Then all players have 0 points