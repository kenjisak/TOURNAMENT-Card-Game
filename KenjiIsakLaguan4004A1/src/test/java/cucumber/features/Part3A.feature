Feature: scenario A
  Game ends with no winner
  
  Scenario: There are 2 rounds with 1 melee each played out and the game ends with no winners
    Given Game initializes with 50 hp for 3 players
    And Round 1 starts as well as distributes each players cards
    And the Melee 1 starts