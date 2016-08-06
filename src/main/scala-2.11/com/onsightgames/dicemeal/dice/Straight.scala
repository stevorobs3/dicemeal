package com.onsightgames.dicemeal.dice

case class Straight(dice: List[Die]) extends ScoringDice{
  val score = 1500
  private val sortedDice = dice.map(_.value).sorted
  private val min = sortedDice.head
  private val max = sortedDice.last
  val name = s"Straight($min -> $max)"
}