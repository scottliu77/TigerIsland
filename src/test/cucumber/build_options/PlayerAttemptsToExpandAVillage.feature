Feature: Expanding a Village Conditions

  Scenario: Trying to expand into a volcano and failing
    Given a settlement that is of your own color and an adjacent Volcano hex
    When a player attempts to expand
    Then the move is rejected

  Scenario: Trying to expand past immediate adjacent hexes and succeeding
    Given a settlement with valid adjacent hexes
    When a player attempts to expand
    Then the move is accepted

  Scenario: Trying to expand without enough pieces and failing
    Given a player has fewer pieces than needed to expand
    When a player attempts to expand
    Then the move is rejected

  Scenario: Expanding a settlement and placing the correct number of villagers
    Given a settlement with valid adjacent hexes
    When a player attempts to expand
    Then the player has the correct amount of remaining villagers