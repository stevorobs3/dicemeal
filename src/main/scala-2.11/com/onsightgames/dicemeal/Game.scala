package com.onsightgames.dicemeal

import com.onsightgames.dicemeal.dice._

case class Game(
  players: List[Player]
) {
  val MaxScore = 10000
  val StartingScore = 500
  val NumDice = 6

  val dice = new Dice(NumDice)


  def gameIsOver: Boolean = {
    players.exists(_.score >= MaxScore)
  }

  def winner: Option[Player] = {
    players
      .filter(_.score <= MaxScore)
      .sortBy(- _.score)
      .headOption
  }

  def calculateScoringDice(diceRolls: List[Die], roundNumber: Int):List[ScoringDice] = {
    val fiveRolls     = diceRolls.filter(_.value == 5).map(SingleFive)
    val oneRolls      = diceRolls.filter(_.value == 1).map(SingleOne)
    val threeOfAKinds = calculateNOfAKinds(diceRolls, 3)
    val fourOfAKinds  = calculateNOfAKinds(diceRolls, 4)
    val fiveOfAKinds  = calculateNOfAKinds(diceRolls, 5)
    val sixOfAKinds   = calculateNOfAKinds(diceRolls, 6)
    val straights     = if (((1 to 6).toList diff diceRolls.map(_.value)) == Nil) List(Straight(diceRolls)) else Nil
    val threePairs    = if (hasThreePairs(diceRolls)) List(ThreePairs(diceRolls)) else Nil

    fiveRolls :::
      oneRolls :::
      threeOfAKinds :::
      fourOfAKinds :::
      fiveOfAKinds :::
      sixOfAKinds :::
      straights  :::
      threePairs
  }

  private def hasThreePairs(diceRolls: List[Die]) = {
    val groupRolls = diceRolls.groupBy(_.value)
    groupRolls.size == 3 && groupRolls.forall(_._2.length == 2)

  }

  private def calculateNOfAKinds(diceRolls: List[Die], n: Int): List[ScoringDice] = {
    diceRolls
      .groupBy(_.value)
      .filter(_._2.length >= n)
      .map{case (_,die) =>
        val nOfAKind = n match {
          case 3 => ThreeOfAKind
          case 4 => FourOfAKind
          case 5 => FiveOfAKind
          case 6 => SixOfAKind
        }
        nOfAKind(die.head, die)
      }
      .toList
  }
}