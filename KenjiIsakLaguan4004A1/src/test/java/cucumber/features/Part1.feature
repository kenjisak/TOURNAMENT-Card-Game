Feature: Melee loser and injury points
  Verify correctness of the Loser and their Injury points after playing a melee

  Scenario Outline: Every player in a 4 player game plays a card and a loser is determined
    Given there are 4 players in a game with 50 hp each and players hands were handed out
    And player 1 hand is rigged with just <first>
    And player 2 hand is rigged with just <second>
    And player 3 hand is rigged with just <third>
    And player 4 hand is rigged with just <forth>
    When player 1 plays <first> card
    When player 2 plays <second> card
    When player 3 plays <third> card
    When player 4 plays <forth> card
    Then the loser will be <loser> and receives <injury points> injury points

    Examples:
      | first                  | second          | third           | forth           | loser  | injury points |
      #1 Testing All arrows – none poisoned
      | "Arrows_13"            | "Arrows_5"      | "Arrows_12"     | "Arrows_7"      | "2"    | 20            |
      #2 Testing All swords – two poisoned
      | "Swords_6"             | "Swords_7"      | "Swords_15"     | "Swords_13"     | "1"    | 30            |
      #3 Testing All sorcery – all poisoned
      | "Sorcery_11"           | "Sorcery_12"    | "Sorcery_6"     | "Sorcery_5"     | "4"    | 40            |
      #4 Testing All deception – completes loser positions
      | "Deception_9"          | "Deception_14"  | "Deception_1"   | "Deception_5"   | "3"    | 25            |
      #5 Testing Merlin loses
      | "Arrows_13"            | "Arrows_8"      | "Merlin_7"      | "Arrows_14"     | "3"    | 45            |
      #6 Testing Merlin is not loser – use Alchemy
      | "Arrows_13"            | "Arrows_8"      | "Merlin_15"     | "Alchemy_14"    | "2"    | 45            |
      #7 Testing Apprentice loses
      | "Arrows_13"            | "Arrows_8"      | "Apprentice_7"  | "Arrows_14"     | "3"    | 25            |
      #8 Testing Apprentice is not loser
      | "Arrows_13"            | "Arrows_8"      | "Apprentice_15" | "Arrows_14"     | "2"    | 25            |
      #9 Testing 3-same card feint
      | "Deception_13"         | "Merlin_14"     | "Merlin_14"     | "Merlin_14"     | "1"    | 80            |
      #10 Testing Mix of Me and Ap
      | "Deception_8"          | "Merlin_14"     | "Deception_9"   | "Apprentice_10" | "1"    | 45            |
      #11 Testing 1 feint 2 cards – feint lowest
      | "Swords_10"            | "Swords_1"      | "Swords_2"      | "Merlin_1"      | "3"    | 40            |
      #12 Testing 1 feint 3 distinct cards
      | "Swords_10"            | "Apprentice_10" | "Swords_15"     | "Merlin_10"     | "3"    | 40            |
      #13 Testing 1 feint 2 cards – feint non lowest
      | "Swords_10"            | "Swords_1"      | "Alchemy_2"     | "Merlin_2"      | "2"    | 40            |
      #14 Testing Start with alchemy and lose
      | "Alchemy_2"            | "Deception_7"   | "Swords_6"      | "Arrows_8"      | "1"    | 35            |
      #15 Testing Start with Al, not lose, using Me and Ap
      | "Alchemy_6"            | "Merlin_7"      | "Apprentice_8"  | "Sorcery_5"     | "4"    | 45            |
      #16 Testing Start with alchemy and not lose
      | "Alchemy_12"           | "Deception_7"   | "Swords_6"      | "Arrows_8"      | "3"    | 35            |
      #17 Testing Merlin starts and is not loser
      | "Merlin_Swords_13"     | "Swords_10"     | "Swords_1"      | "Alchemy_2"     | "3"    | 40            |
      #18 Testing Apprentice starts and is not loser
      | "Apprentice_Swords_13" | "Swords_10"     | "Swords_1"      | "Swords_2"      | "3"    | 20            |
      #19 Testing Feint makes Me loses despite playing first
      | "Merlin_Swords_13"     | "Swords_10"     | "Alchemy_10"    | "Apprentice_10" | "1"    | 40            |
      #20 Testing Feint makes Ap loses despite playing first
      | "Apprentice_Swords_13" | "Swords_10"     | "Alchemy_10"    | "Apprentice_10" | "1"    | 20            |
      #21 Testing 2 merlins 2nd one in suit + 1 poisoned
      | "Merlin_Deception_13"  | "Deception_7"   | "Merlin_14"     | "Deception_10"  | "2"    | 70            |
      #22 Testing 2 merlins + Ap in suit + 1 poisoned
      | "Merlin_Deception_13"  | "Apprentice_7"  | "Merlin_14"     | "Deception_10"  | "2"    | 65            |
      #23 Testing 2 feints – no loser
      | "Swords_10"            | "Apprentice_10" | "Swords_11"     | "Merlin_11"     | "null" | 0             |
      #24 Testing Same value for all players – no loser
      | "Swords_10"            | "Apprentice_10" | "Alchemy_10"    | "Merlin_10"     | "null" | 0             |