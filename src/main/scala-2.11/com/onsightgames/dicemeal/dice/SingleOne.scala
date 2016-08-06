package com.onsightgames.dicemeal.dice

case class SingleOne(die: Die)  extends ScoringDice{
  val score = 100
  val dice = List(die)
  val name = "SingleOne"
}