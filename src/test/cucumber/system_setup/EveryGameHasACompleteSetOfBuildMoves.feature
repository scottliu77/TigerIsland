Feature: System Setup

  Scenario: Every game has a complete set of rules
    Given a game is created
      And that game has players
    When the game has not yet started
    Then then the game has rules the following moves:
      | VillageCreation |
      | VillageExpansion |
      | TotoroPlacement |
      | TigerPlacement |