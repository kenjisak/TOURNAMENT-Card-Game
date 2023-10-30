Feature: Loser and Injury points are determined each melee
  Check correct scoring behavior for each category

  Scenario Outline: User scores correctly in a category
    Given testing1 <first>
    And testing2 <second>
    And testing3 <third>
    And testing4 <forth>
    Then loser <loser>

    Examples:
      | first             | second           | third             | forth            | loser     | injury points |
      # Testing
      | "Basic_Arrows_13" | "Basic_Arrows_5" | "Basic_Arrows_12" | "Basic_Arrows_7" | "2"     | 20            |