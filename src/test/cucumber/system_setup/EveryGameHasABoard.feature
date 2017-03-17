Feature: System Startup

  Scenario: Every game has a board
    Given a game is created
    And that game has players
    When the game is started
    Then then the game has a board