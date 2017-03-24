Feature: Tile Placement

  Scenario: Placing tile at height one not next to existing pieces
    Given existing pieces on a board
    When a player places a tile at height one not directly next to existing pieces
    Then deny the player from doing so

  Scenario: Placing tile at height one next to existing pieces
    Given existing pieces on a board
    When a player places a tile at height one that is directly next to existing pieces
    Then do not deny the player from doing so