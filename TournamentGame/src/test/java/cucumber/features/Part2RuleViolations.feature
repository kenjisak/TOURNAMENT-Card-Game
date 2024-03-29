Feature: Robustness
  Attempting to break the rules

  Scenario: 4 Melees are played showing the attempts of violating most the game rules
    Given the game starts
    When user enters 1 for number of players
    Then user receives invalid "Invalid Number Entered." message
    When user enters 3 for number of players
    And user enters "" name for player 1
    Then user receives invalid "Empty Name Entered." message
    When user enters "Fred" name for player 1
    And user enters "" name for player 2
    Then user receives invalid "Empty Name Entered." message
    When user enters "Joe" name for player 1
    And user enters "" name for player 3
    Then user receives invalid "Empty Name Entered." message
    When user enters "Paul" name for player 1
    And user enters -10 for initial health points
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
    And "Player" "Joe" plays "Swords_2"
    Then "Joe" receives invalid "This Card doesn't match the Suit of this Melee: Sorcery" card message
    When "Player" "Joe" plays "Deception_2"
    Then "Joe" receives invalid "This Card doesn't match the Suit of this Melee: Sorcery" card message
    When "Player" "Joe" plays "Arrows_2"
    Then "Joe" receives invalid "This Card doesn't match the Suit of this Melee: Sorcery" card message
    When "Player" "Joe" plays "Alchemy_2"
    Then "Joe" receives invalid "You cannot play an Alchemy card, with other playable cards in your hand." card message
    When "Player" "Joe" plays "Sorcery_6"
    And "Player" "Paul" plays "Sorcery_7"
    Then "Joe" is the loser with 25 injury points for this melee, total round injury points is 25

    Given Melee 2 starts
    And "Joe" hand is rigged with "Arrows_8"
    And "Paul" hand is rigged with "Merlin"
    And "Fred" hand is rigged with "Apprentice"
    When "Leader" "Joe" plays "Arrows_8"
    And "Player" "Paul" plays "Merlin_16_9"
#    Need to combine invalid and valid input as it needs to be done in one step bc of the while loop for asking of the value
    Then "Paul" receives invalid "Invalid Value Entered." card message
    When "Player" "Fred" plays "Apprentice_20_10"
#    Need to combine invalid and valid input as it needs to be done in one step bc of the while loop for asking of the value
    Then "Fred" receives invalid "Invalid Value Entered." card message
    And "Joe" is the loser with 40 injury points for this melee, total round injury points is 65

    Given Melee 3 starts
    And "Joe" hand is rigged with "Swords_9"
    And "Paul" hand is rigged with "Alchemy_2,Swords_7"
    And "Fred" hand is rigged with "Swords_3"
    When "Leader" "Joe" plays "Swords_9"
    And "Player" "Paul" plays "Alchemy_2"
    Then "Paul" receives invalid "You cannot play an Alchemy card, with other playable cards in your hand." card message
    When "Player" "Paul" plays "Swords_7"
    And "Player" "Fred" plays "Swords_3"
    Then "Fred" is the loser with 25 injury points for this melee, total round injury points is 25

    Given Melee 4 starts
    And "Fred" hand is rigged with "Deception_9"
    And "Joe" hand is rigged with "Alchemy_2,Deception_6"
    And "Paul" hand is rigged with "Deception_1"
    When "Leader" "Fred" plays "Deception_9"
    And "Player" "Joe" plays "Alchemy_2"
    Then "Joe" receives invalid "You cannot play an Alchemy card, with other playable cards in your hand." card message
    When "Player" "Joe" plays "Deception_6"
    And "Player" "Paul" plays "Deception_1"
#    The Game doesn't shame a player if they have playable cards, so Paul isn't able to be shamed since he has a Deception in his Deck
#    "Tries" to shame isn't possible, while being able to still play a card
    Then "Paul" is the loser with 25 injury points for this melee, total round injury points is 25