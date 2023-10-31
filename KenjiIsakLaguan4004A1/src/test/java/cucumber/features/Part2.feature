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