Feature: No Winners Scenario A
  Game ends with no winner
  
  Scenario: There are 2 rounds played and the game ends with no winners
    Given Game initializes with 50 hp for 3 players
    And Round 1 starts, as well as distributes each players cards
    And player 1 is rigged with "Sorcery_1,Swords_1" cards
    And player 2 is rigged with "Alchemy_1,Alchemy_2" cards
    And player 3 is rigged with "Merlin,Merlin" cards

    When the Melee 1 starts
    Then "P1" is the current leader
    When player 1 plays their "Sorcery_1" card
    And player 2 plays their "Alchemy_1" card
    And player 3 plays their "Merlin_1" card
    Then there is No loser for this melee

    When the Melee 2 starts
    Then "P1" is the current leader
    When player 1 plays their "Swords_1" card
    And player 2 plays their "Alchemy_2" card
    And player 3 plays their "Merlin_3" card
    Then "P1" will be the loser for this melee

    When Round 1 is over
    Given Round 2 starts, as well as distributes each players cards
    And player 1 is rigged with "Swords_6,Arrows_9,Merlin,Sorcery_11" cards
    And player 2 is rigged with "Swords_7,Arrows_10,Merlin,Sorcery_12" cards
    And player 3 is rigged with "Swords_8,Arrows_8,Deception_5,Sorcery_6" cards

    When the Melee 1 starts
    Then "P2" is the current leader
    When player 2 plays their "Swords_7" card
    And player 3 plays their "Swords_8" card
    And player 1 plays their "Swords_6" card
    Then "P1" will be the loser for this melee

    When the Melee 2 starts
    And player 1 plays their "Arrows_9" card
    And player 2 plays their "Arrows_10" card
    And player 3 plays their "Arrows_8" card
    Then "P3" will be the loser for this melee

    When the Melee 2 starts
    And player 3 plays their "Deception_5" card
    And player 1 plays their "Merlin_10" card
    And player 2 plays their "Merlin_3" card
    Then "P2" will be the loser for this melee

    When the Melee 3 starts
    And player 2 plays their "Sorcery_12" card
    And player 3 plays their "Sorcery_6" card
    And player 1 plays their "Sorcery_11" card
    Then "P3" will be the loser for this melee

    When Round 2 is over
    Then the Game is over with no winners