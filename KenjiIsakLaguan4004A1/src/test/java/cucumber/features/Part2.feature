Feature: Robustness
  Attempting to break the rules

  Scenario: a bunch of rule breaking
    Given the game starts
    When user enters 1 for number of players
    Then user receives invalid "Invalid Number Entered." message
    When user enters 3 for number of players
    Then user doesn't receive the "Invalid Number Entered." violation message
    When user enters "" name for player 1
    Then user receives invalid "Empty Name Entered." message
    When user enters "Fred" name for player 1
    Then user doesn't receive the "Empty Name Entered." violation message
    When user enters "" name for player 2
    Then user receives invalid "Empty Name Entered." message
    When user enters "Joe" name for player 1
    Then user doesn't receive the "Empty Name Entered." violation message
    When user enters "" name for player 3
    Then user receives invalid "Empty Name Entered." message
    When user enters "Paul" name for player 1
    Then user doesn't receive the "Empty Name Entered." violation message
    When user enters -10 for initial health points
    Then user receives invalid "Invalid HP Number Entered." message
    When user enters 50 for initial health points
    Then Game initializes with 50 hp for 3 players named "Fred,Joe,Paul"

    Given Round 1 starts and distributes each players cards
    And Melee 1 starts
    And "Fred" hand is rigged with "Alchemy_1,Sorcery_11,Merlin,Apprentice"
    And "Joe" hand is rigged with "Swords_2,Deception_2,Arrows_2,Alchemy_2,Sorcery_6"
    And "Paul" hand is rigged with "Sorcery_7"
    When "Leader" "Fred" plays "Alchemy_1"
    Then "Fred" receives invalid "You cannot start with an Alchemy card, with other type of cards left in hand." card message
    When "Leader" "Fred" plays "Sorcery_11"
    Then user doesn't receive the "You cannot start with an Alchemy card, with other type of cards left in hand." violation message
    When "Player" "Joe" plays "Swords_2"
    Then "Joe" receives invalid "This Card doesn't match the Suit of this Melee: Sorcery" card message
    When "Player" "Joe" plays "Deception_2"
    Then "Joe" receives invalid "This Card doesn't match the Suit of this Melee: Sorcery" card message
    When "Player" "Joe" plays "Arrows_2"
    Then "Joe" receives invalid "This Card doesn't match the Suit of this Melee: Sorcery" card message
    When "Player" "Joe" plays "Alchemy_2"
    Then "Joe" receives invalid "You cannot play an Alchemy card, with other playable cards in your hand." card message
    When "Player" "Joe" plays "Sorcery_6"
    Then user doesn't receive the "This Card doesn't match the Suit of this Melee: Sorcery" violation message
    And user doesn't receive the "You cannot play an Alchemy card, with other playable cards in your hand." violation message
    When "Player" "Paul" plays "Sorcery_7"
    Then "Joe" is the loser with 25 injury points for this melee, total round injury points is 25

    Given Melee 2 starts
    And "Joe" hand is rigged with "Arrows_8"
    And "Paul" hand is rigged with "Merlin"
    And "Fred" hand is rigged with "Apprentice"
    When "Leader" "Joe" plays "Arrows_8"
#    When "Player" "Paul" plays "Merlin_16"
#    When "Player" "Paul" inputs 9 as the value to his "Merlin" card