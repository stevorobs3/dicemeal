package com.onsightgames.dicemeal.dice

case class ThreePairs(dice: List[Die]) extends ScoringDice{
  val score = 500

  private val pairs = dice.map(_.value).distinct

  val name = s"ThreePairs(${pairs.mkString(",")})"
}