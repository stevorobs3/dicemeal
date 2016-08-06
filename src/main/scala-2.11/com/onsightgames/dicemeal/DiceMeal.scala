package com.onsightgames.dicemeal

import com.onsightgames.dicemeal.dice.Die

import scala.util.Random

object DiceMeal {

  private var seed = 0
  private val MaxRoll = 6
  private val random = new Random(seed)

  def SetSeed(newSeed: Int): Unit = {
    seed = newSeed
    random.setSeed(newSeed)
  }

  def nextRoll(): Int = {
    1 + random.nextInt(MaxRoll - 1)
  }

  def calculateScore(dice: List[Die]) = {

  }
}
