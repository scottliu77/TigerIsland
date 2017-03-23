Feature: System Setup

  Scenario: Every game has a board
    Given a game is created
      And that game has players
    When the game has not yet started
    Then then the game has a board