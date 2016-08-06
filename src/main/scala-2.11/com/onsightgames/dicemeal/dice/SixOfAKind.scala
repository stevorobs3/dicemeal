package com.onsightgames.dicemeal.dice

case class SixOfAKind(die: Die, dice : List[Die]) extends ScoringDice {
  val score = FiveOfAKind(die, dice : List[Die]).score*2

  val name = s"SixOfAKind(${die.value})"
}
