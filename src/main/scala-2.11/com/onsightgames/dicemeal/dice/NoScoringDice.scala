package com.onsightgames.dicemeal.dice

case object NoScoringDice extends ScoringDice{
  val score = -3000
  val dice = Nil
  val name = s"NoScoringDice"
}