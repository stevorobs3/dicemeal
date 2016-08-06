package com.onsightgames.dicemeal.dice

case class FiveOfAKind(die: Die, dice : List[Die]) extends ScoringDice {
  val score = FourOfAKind(die, dice : List[Die]).score*2
  val name = s"FiveOfAKind(${die.value}"
}
