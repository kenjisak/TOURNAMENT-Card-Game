Feature: Melee loser and injury points
  Verify correctness of the Loser and their Injury points after playing a melee

  Scenario Outline: Every player in a 4 player game plays a card and a loser is determined
    Given there are 4 players in a game and players hands were handed out
    When player 1 plays <first> card
    When player 2 plays <second> card
    When player 3 plays <third> card
    When player 4 plays <forth> card
    Then the loser will be <loser> and receives <injury points> injury points

    Examples:
      | first                  | second               | third               | forth                | loser  | injury points |
      #1 Testing All arrows – none poisoned
      | "Basic_Arrows_13"      | "Basic_Arrows_5"     | "Basic_Arrows_12"   | "Basic_Arrows_7"     | "2"    | 20            |
      #2 Testing All swords – two poisoned
      | "Basic_Swords_6"       | "Basic_Swords_7"     | "Basic_Swords_15"   | "Basic_Swords_13"    | "1"    | 30            |
      #3 Testing All sorcery – all poisoned
      | "Basic_Sorcery_11"     | "Basic_Sorcery_12"   | "Basic_Sorcery_6"   | "Basic_Sorcery_5"    | "4"    | 40            |
      #4 Testing All deception – completes loser positions
      | "Basic_Deception_9"    | "Basic_Deception_14" | "Basic_Deception_1" | "Basic_Deception_5"  | "3"    | 25            |
      #5 Testing Merlin loses
      | "Basic_Arrows_13"      | "Basic_Arrows_8"     | "Merlin_7"          | "Basic_Arrows_14"    | "3"    | 45            |
      #6 Testing Merlin is not loser – use Alchemy
      | "Basic_Arrows_13"      | "Basic_Arrows_8"     | "Merlin_15"         | "Alchemy_14"         | "2"    | 45            |
      #7 Testing Apprentice loses
      | "Basic_Arrows_13"      | "Basic_Arrows_8"     | "Apprentice_7"      | "Basic_Arrows_14"    | "3"    | 25            |
      #8 Testing Apprentice is not loser
      | "Basic_Arrows_13"      | "Basic_Arrows_8"     | "Apprentice_15"     | "Basic_Arrows_14"    | "2"    | 25            |
      #9 Testing 3-same card feint
      | "Basic_Deception_13"   | "Merlin_14"          | "Merlin_14"         | "Merlin_14"          | "1"    | 80            |
      #10 Testing Mix of Me and Ap
      | "Basic_Deception_8"    | "Merlin_14"          | "Basic_Deception_9" | "Apprentice_10"      | "1"    | 45            |
      #11 Testing 1 feint 2 cards – feint lowest
      | "Basic_Swords_10"      | "Basic_Swords_1"     | "Basic_Swords_2"    | "Merlin_1"           | "3"    | 40            |
      #12 Testing 1 feint 3 distinct cards
      | "Basic_Swords_10"      | "Apprentice_10"      | "Basic_Swords_15"   | "Merlin_10"          | "3"    | 40            |
      #13 Testing 1 feint 2 cards – feint non lowest
      | "Basic_Swords_10"      | "Basic_Swords_1"     | "Alchemy_2"         | "Merlin_2"           | "2"    | 40            |
      #14 Testing Start with alchemy and lose
      | "Alchemy_2"            | "Basic_Deception_7"  | "Basic_Swords_6"    | "Basic_Arrows_8"     | "1"    | 35            |
      #15 Testing Start with Al, not lose, using Me and Ap
      | "Alchemy_6"            | "Merlin_7"           | "Apprentice_8"      | "Basic_Sorcery_5"    | "4"    | 45            |
      #16 Testing Start with alchemy and not lose
      | "Alchemy_12"           | "Basic_Deception_7"  | "Basic_Swords_6"    | "Basic_Arrows_8"     | "3"    | 35            |
      #17 Testing Merlin starts and is not loser
      | "Merlin_Swords_13"     | "Basic_Swords_10"    | "Basic_Swords_1"    | "Alchemy_2"          | "3"    | 40            |
      #18 Testing Apprentice starts and is not loser
      | "Apprentice_Swords_13" | "Basic_Swords_10"    | "Basic_Swords_1"    | "Basic_Swords_2"     | "3"    | 20            |
      #19 Testing Feint makes Me loses despite playing first
      | "Merlin_Swords_13"     | "Basic_Swords_10"    | "Alchemy_10"        | "Apprentice_10"      | "1"    | 40            |
      #20 Testing Feint makes Ap loses despite playing first
      | "Apprentice_Swords_13" | "Basic_Swords_10"    | "Alchemy_10"        | "Apprentice_10"      | "1"    | 20            |
      #21 Testing 2 merlins 2nd one in suit + 1 poisoned
      | "Merlin_Deception_13"  | "Basic_Deception_7"  | "Merlin_14"         | "Basic_Deception_10" | "2"    | 70            |
      #22 Testing 2 merlins + Ap in suit + 1 poisoned
      | "Merlin_Deception_13"  | "Apprentice_7"       | "Merlin_14"         | "Basic_Deception_10" | "2"    | 65            |
      #23 Testing 2 feints – no loser
      | "Basic_Swords_10"      | "Apprentice_10"      | "Basic_Swords_11"   | "Merlin_11"          | "null" | 0             |
      #24 Testing Same value for all players – no loser
      | "Basic_Swords_10"      | "Apprentice_10"      | "Alchemy_10"        | "Merlin_10"          | "null" | 0             |