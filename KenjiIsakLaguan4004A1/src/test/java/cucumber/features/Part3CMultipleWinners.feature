Feature: Scenario C Multiple Winners
  Game ends with multiple winners

  Scenario: 1 Round and 1 Melee is played and the game ends with multiple winners
    Given Game initializes with 25 hp for 3 players
    And Round 1 starts, as well as distributes each players cards
    And player 1 is rigged with "Merlin" cards
    And player 2 is rigged with "Alchemy_8" cards
    And player 3 is rigged with "Sorcery_6" cards

    When the Melee 1 starts
    And player 1 plays their "Merlin_Sorcery_1" card
    And player 2 plays their "Alchemy_8" card
    And player 3 plays their "Sorcery_6" card
    Then "P1" will be the loser for this melee

    When Round 1 is over
    Then the Game ends with "P2,P3" as the winner(s) and not "P1"