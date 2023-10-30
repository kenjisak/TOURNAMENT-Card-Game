Feature: Loser and Injury points are determined each melee
  Check correct scoring behavior for each category

  Scenario Outline: Loser receives injury points correctly in a melee
    Given there are 4 players in a game
    When player 1 plays <first> card
    When player 2 plays <second> card
    When player 3 plays <third> card
    When player 4 plays <forth> card
    Then the loser will be <loser> and receives <injury points> injury points

    Examples:
      | first             | second           | third             | forth            | loser     | injury points |
      # Testing
      | "Basic_Arrows_13" | "Basic_Arrows_5" | "Basic_Arrows_12" | "Basic_Arrows_7" |    "2"    |      20       |
      | "Basic_Swords_6"  | "Basic_Swords_7" | "Basic_Swords_15" | "Basic_Swords_13"|    "1"    |      30       |
      | "Basic_Sorcery_11" | "Basic_Sorcery_12"| "Basic_Sorcery_6" | "Basic_Sorcery_5"|    "4"    |      40       |
      | "Basic_Deception_9" | "Basic_Deception_14"| "Basic_Deception_1" | "Basic_Deception_5"|    "3"    |      25       |
      | "Basic_Arrows_13" | "Basic_Arrows_8"| "Merlin_7" | "Basic_Arrows_14"|    "3"    |      45       |