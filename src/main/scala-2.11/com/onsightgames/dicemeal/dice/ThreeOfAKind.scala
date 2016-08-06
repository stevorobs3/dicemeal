package com.onsightgames.dicemeal.dice

case class ThreeOfAKind(die: Die, dice : List[Die]) extends ScoringDice{
  require(die.value >= 1 && die.value <= 6)
  val score =
    if (die.value == 1)
      1000
    else
      die.value * 100

  val name = s"ThreeOfAKind(${die.value})"
}