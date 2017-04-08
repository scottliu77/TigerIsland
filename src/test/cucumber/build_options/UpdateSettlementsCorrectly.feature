Feature: Updating Settlement Conditions

  Scenario: A settlement is created, the settlement count should increase by 1
    Given a player creates a new settlement
    When the turn is over and updateSettlements is called
    Then the number of settlements should increase by one

  Scenario: Combining 2 separate adjacent villages of the same color into 1
    Given a player has two villages adjacent to one another
    When the turn is over and updateSettlements is called
    Then there should only be one settlement

  Scenario: Ensuring 2 adjacent settlements of different colors do not merge into 1
    Given two settlements of different color are adjacent
    When the turn is over and updateSettlements is called
    Then there should be two settlements, not one

  Scenario: Combining 2 settlements of the same color after 1 expands and becomes adjacent to the other
    Given a player has expanded an existing settlement becoming adjacent to another settlement of the same color
    When the turn is over and updateSettlements is called
    Then there should only be one settlement

  Scenario: Ensuring 2 settlements of different color do not combine after 1 expands and becomes adjacent to the other
    Given a player expands an existing settlement becoming adjacent to a different colored settlement
    When the turn is over and updateSettlements is called
    Then there should be two settlements, not one

  Scenario: Combining 2 settlements by placing a totoro on an adjacent hex to both
    Given a player builds a totoro sanctuary connecting two settlements
    When the turn is over and updateSettlements is called
    Then there should only be one settlement

  Scenario: Combining 2 settlements by placing a totoro directly next to a totoro existing in the other settlement
    Given a player builds a totoro directly next to a totoro in another settlement
    When the turn is over and updateSettlements is called
    Then there should only be one settlement

  Scenario: Ensuring 2 settlements of different color do not combine if a totoro is placed between both
    Given a player builds a totoro sanctuary between two different colored settlements
    When the turn is over and updateSettlements is called
    Then there should be two settlements, not one

  Scenario: Combining 2 settlements by placing a tiger on an adjacent valid hex to both
    Given a player builds a tiger connecting two settlements
    When the turn is over and updateSettlements is called
    Then there should only be one settlement

  Scenario: Combining 2 settlements by placing a tiger directly next to a tiger existing in the other settlement
    Given a player builds a tiger directly next to a tiger in another settlement
    When the turn is over and updateSettlements is called
    Then there should only be one settlement

  Scenario: Ensuring 2 settlements of different color do not combine if a tiger is placed between both
    Given a player builds a tiger between two different colored settlements
    When the turn is over and updateSettlements is called
    Then there should be two settlements, not one

  Scenario: Combining 2 settlements connected by placing a totoro while the other settlement has a tiger already
    Given a player has a settlement containing a tiger and another capable of placing a totoro
    When the turn is over and updateSettlements is called
    Then there should only be one settlement

  Scenario: Combining 2 settlements connected by placing a tiger while the other settlement has a totoro already
    Given a player has a settlement containing a totoro and another capable of placing a tiger
    When the turn is over and updateSettlements is called
    Then there should only be one settlement