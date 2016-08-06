package com.onsightgames.dicemeal.dice

trait ScoringDice{
  val score: Int
  val dice: List[Die]

  val name: String

  override def toString: String = s"$name = $score"
}
