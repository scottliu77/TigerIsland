Feature: Tile Placement

  Scenario: Nuking pieces
    Given existing pieces and entities on a board
    When a player nukes existing meeples that do not comprise a full settlment
    Then do not deny the player from doing so

  Scenario: Nuking Individual Pieces
    Given existing pieces and entities on a board
    When a player attempts to nuke a single hex settlment
    Then deny the player from doing so

  Scenario: Nuking size two settlment
    Given existing pieces and entities on a board
    When a player attempts to nuke a size two hex settlment
    Then deny the player from doing so

  Scenario: Nuking Totoros
    Given existing pieces and entities on a board
    When a player attempts to nuke a Totoro
    Then deny the player from doing so

  Scenario: Nuking Tigers
    Given an existing settlement with a Tiger
    When a player attempts to nuke a Tiger
    Then deny the player from doing so