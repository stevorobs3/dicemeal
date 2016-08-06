package com.onsightgames.dicemeal.dice

class Dice(count : Int) {

  val dice = List.fill(count)(new Die)

  var remainingDice = dice

  def useDice(diceToRemove: List[Die]): Unit = {
    remainingDice = remainingDice.filterNot(diceToRemove contains _)
  }

  def resetDice(): Unit = {
    remainingDice = dice
  }
}
