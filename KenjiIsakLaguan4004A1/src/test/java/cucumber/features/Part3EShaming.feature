Feature: Scenario E Shaming
  Showing most cases of shaming during a melee

  Scenario: 4 Melees are played in 1 Round, showing shaming functionality with feints in melee
    Given Game initializes with 35 hp for 4 players
    And Round 1 starts, as well as distributes each players cards
    And player 1 is rigged with "Deception_6,Merlin,Apprentice,Alchemy_5" cards
    And player 2 is rigged with "Deception_10,Sorcery_15,Sorcery_12,Deception_2" cards
    And player 3 is rigged with "Arrows_7,Swords_5,Swords_11,Arrows_8" cards
    And player 4 is rigged with "Deception_8,Swords_9,Apprentice,Alchemy_10" cards

    When the Melee 1 starts
    And player 1 plays their "Deception_6" card
    And player 2 plays their "Deception_10" card
    And player 3 is shamed and discards their "Arrows_7" card
    And player 4 plays their "Deception_8" card
    Then "P1" will be the loser for this melee

    When the Melee 2 starts
    And player 1 plays their "Merlin_Swords_5" card
    And player 2 is shamed and discards their "Sorcery_15" card
    And player 3 plays their "Swords_5" card
    And player 4 plays their "Swords_9" card
    Then "P4" will be the loser for this melee

    When the Melee 3 starts
    And player 4 plays their "Apprentice_Sorcery_12" card
    And player 1 plays their "Apprentice_12" card
    And player 2 plays their "Sorcery_12" card
    And player 3 is shamed and discards their "Swords_11" card
    Then there is No loser for this melee
    And "P4" is the current leader

    When the Melee 4 starts
    And player 4 plays their "Alchemy_10" card
    And player 1 plays their "Alchemy_5" card
    And player 2 plays their "Deception_2" card
    And player 3 plays their "Arrows_8" card
    Then "P2" will be the loser for this melee

    When Round 1 is over
    Then the Game ends with "P3" as the winner(s) and not "P1,P2,P4"