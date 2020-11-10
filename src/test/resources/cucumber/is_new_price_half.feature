Feature: Is new Price half?
  Is the price or an item correctly halved

Scenario Outline: Food item price has been successfully halved
  Given price is <price>
  When I say to half it
  Then I should be told <answer>

  Examples:
  |price        |answer |
  |2            |1      |
  |10           |5      |
  |10.5         |5.25   |