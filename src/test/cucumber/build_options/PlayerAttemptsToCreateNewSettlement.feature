Feature: Create new village conditions

 Scenario: Trying to create new village on a volcano
  Given a Volcano hex
  When a player attempts to create new village on the hex
  Then the move is rejected

 Scenario: Trying to create new village on occupied hex
  Given an occupied hex
  When a player attempts to create new village on the hex
  Then the move is rejected

 Scenario: Trying to create new village when player has no more villagers
  Given there is a valid hex
  And the player has no villagers
  When a player attempts to create new village on the hex
  Then the move is rejected

 Scenario: Trying to create new village when player has no more shaman
  Given there is a valid hex
  And the player has no shaman
  When a player attempts to create new village on the hex with a shaman
  Then the move is rejected

 Scenario: Trying to create new village on hex of height larger than one
  Given a nonvolcanic hex of height greater than one
  When a player attempts to create new village on the hex
  Then the move is rejected

 Scenario: Trying to create a new village on a nonexistent hex
  Given a nonexistent hex location
  When a player attempts to create new village on the hex
  Then the move is rejected

 Scenario: Successfully create new village
  Given there is a valid hex
  When a player attempts to create new village on the hex
  Then the move is accepted
