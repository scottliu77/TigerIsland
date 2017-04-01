Feature: Create new village conditions

 Scenario: Trying to create new village on a volcano
  Given a Volcano hex
  When a player attempts to create new village on the hex
  Then the move is rejected

 Scenario: Trying to create new village on occupied hex
  Given an occupied hex
  When a player attempts to create new village on the hex
  Then the move is rejected

 #Scenario: Trying to create new village with no remaining villagers
  #Given: a player has no more villagers
 # And there is a valid hex
  #When a player attempts to create new village on the hex
 # Then the move is rejected
