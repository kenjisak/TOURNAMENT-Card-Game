Feature: Scenario B Game ends by shaming
  Game ends since a player was shamed and died

  Scenario: Game ends before Round 1 ends due to the 2nd player dying from shaming
    Given Game initializes with 5 hp for 3 players
    And Round 1 starts, as well as distributes each players cards
    And player 1 is rigged with "Arrows_5" cards
    And player 2 is rigged with "Sorcery_1" cards
    And player 3 is rigged with "Arrows_8" cards

    When the Melee 1 starts
    Then "P1" is the current leader
    When player 1 plays their "Arrows_5" card
    And player 2 is shamed and discards their "Alchemy_1" card
    Then the Game ends from player 2 being shamed