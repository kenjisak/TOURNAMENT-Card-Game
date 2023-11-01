Feature: Robustness
  Attempting to break the rules

  Scenario: a bunch of rule breaking
    Given the game starts
    When user enters 1 for number of players
    Then user receives invalid "Invalid Number Entered." message
    When user enters 3 for number of players
    When user enters "" name for player 1
    Then user receives invalid "Empty Name Entered." message
    When user enters "Fred" name for player 1
    When user enters "" name for player 2
    Then user receives invalid "Empty Name Entered." message
    When user enters "Joe" name for player 1
    When user enters "" name for player 3
    Then user receives invalid "Empty Name Entered." message
    When user enters "Paul" name for player 1
    When user enters -10 for initial health points
    Then user receives invalid "Invalid HP Number Entered." message
    When user enters 50 for initial health points

    Given the round starts and distributes each players cards
    And "Fred" hand is rigged with "Alchemy_1,Sorcery_11,Merlin,Apprentice"
    And "Joe" hand is rigged with "Swords_2,Deception_2,Arrows_2,Alchemy_2,Sorcery_6"
    And "Paul" hand is rigged with "Sorcery_7"
    When "Leader" "Fred" plays "Alchemy_1"
    Then "Fred" receives invalid "You cannot start with an Alchemy card, with other type of cards left in hand." card message
    When "Leader" "Fred" plays "Sorcery_11"
    #after the output for valid, assert no error message in it.
    When "Player" "Joe" plays "Swords_2"
    Then "Joe" receives invalid "This Card doesn't match the Suit of this Melee: Sorcery" card message
    When "Player" "Joe" plays "Deception_2"
    Then "Joe" receives invalid "This Card doesn't match the Suit of this Melee: Sorcery" card message
    When "Player" "Joe" plays "Arrows_2"
    Then "Joe" receives invalid "This Card doesn't match the Suit of this Melee: Sorcery" card message
    When "Player" "Joe" plays "Alchemy_2"
    Then "Joe" receives invalid "You cannot play an Alchemy card, with other playable cards in your hand." card message
    When "Player" "Joe" plays "Sorcery_6"
    #after the output for valid, assert no error message in it.
    When "Player" "Paul" plays "Sorcery_7"
    #after the output for valid, assert no error message in it.
    Then "Joe" is the loser with 25 injury points for this melee, total round injury points is 25