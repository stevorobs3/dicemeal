package com.onsightgames.dicemeal.dice

import com.onsightgames.dicemeal.DiceMeal

class Die {
  private var currentRoll = 0
  roll()

  def value: Int = currentRoll

  def roll(): Int = {
    currentRoll = DiceMeal.nextRoll()
    currentRoll
  }

  override def toString(): String = {
    s"Die($value)"
  }
}