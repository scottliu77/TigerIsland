Feature: Offline System Setup

  Scenario: Offline games create their own tile deck
    Given an offline game is created
      And that offline game has players
    When the offline game has not yet started
    Then then the game has a tile deck
      And that deck has 48 total tiles
      And that deck has 3 tiles of each valid combination of terrain types