package com.onsightgames.dicemeal.dice

case class FourOfAKind(die: Die, dice : List[Die]) extends ScoringDice {
  val score = ThreeOfAKind(die, dice : List[Die]).score*2
  val name = s"FourOfAKind(${die.value})"
}
