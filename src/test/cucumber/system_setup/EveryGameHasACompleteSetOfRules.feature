Feature: System Setup

  Scenario: Every game has a complete set of rules
    Given a game is created
    And that game has players
    When the game is started
    Then then the game has rules the following moves:
      | validTilePlacement |
      | validVillageCreation |
      | validVillageExpansion |
      | validTotoroPlacement |