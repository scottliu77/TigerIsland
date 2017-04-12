Feature: Game End Conditions

  Scenario: When a player completely places their villagers and totoro, they win
    Given it is a player's turn
    When they build all villagers and totoro
    Then the game ends

  Scenario: When a player completely places their villagers and tigers, they win
    Given it is a player's turn
    When they build all villagers and tigers
    Then the game ends

  Scenario: When a player completely places their tigers and totoro, they win
    Given it is a player's turn
    When they build all totoro and tigers
    Then the game ends