Feature: System Setup
  
  Scenario: Each Player starts with 20 villagers
    Given a game is created
      And that game has players
    When the game is started
    Then all players have 20 villagers