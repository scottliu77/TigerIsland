Feature: Offline System Setup

  Scenario: Offline games create their own tile deck
    Given a game is created
    And that game has players
    And that is an offline game
    When the game is started
    Then then the game has a board