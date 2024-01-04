Feature: Scenario D One Winner
  Game ends with only one winner

  Scenario: 1 Round and 2 Melees is played and the game ends with one winner
    Given Game initializes with 25 hp for 3 players
    And Round 1 starts, as well as distributes each players cards
    And player 1 is rigged with "Alchemy_8,Alchemy_2" cards
    And player 2 is rigged with "Deception_6,Merlin" cards
    And player 3 is rigged with "Arrows_8,Swords_12" cards

    When the Melee 1 starts
    And player 1 plays their "Alchemy_8" card
    And player 2 plays their "Deception_6" card
    And player 3 plays their "Arrows_8" card
    Then "P2" will be the loser for this melee

    When the Melee 2 starts
    And player 2 plays their "Merlin_Swords_15" card
    And player 3 plays their "Swords_12" card
    And player 1 plays their "Alchemy_2" card
    Then "P1" will be the loser for this melee

    When Round 1 is over
    Then the Game ends with "P3" as the winner(s) and not "P1,P2"