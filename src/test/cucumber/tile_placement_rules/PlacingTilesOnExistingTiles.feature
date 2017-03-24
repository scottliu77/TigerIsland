Feature: Tile Placement

  Scenario: Proper piece placement
    Given existing pieces on a board
    When a player places a tile properly on existing ones
    Then do not deny the player from doing so

  Scenario: Volcano doesnt rest on previous Volcano
    Given existing pieces on a board
    When a player places a tile on another tile and the volcanoes do not overlap
    Then deny the player from doing so

  Scenario: Placing a piece with overhang
    Given existing pieces on a board
    When a player places a tile on another tile with overhang
    Then deny the player from doing so

  Scenario: Placing a tile directly on top of an existing one
    Given existing pieces on a board
    When a player places a tile directly on top of existing one
    Then deny the player from doing so