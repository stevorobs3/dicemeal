package com.onsightgames.dicemeal.dice

case class SingleFive(die: Die) extends ScoringDice{
  val score = 50
  val dice = List(die)

  val name = "SingleFive"
}