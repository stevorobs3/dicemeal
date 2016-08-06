package com.onsightgames.dicemeal

import java.util.regex.PatternSyntaxException

import com.onsightgames.dicemeal.dice.{Dice, NoScoringDice, ScoringDice}

import scala.annotation.tailrec
import scala.io.StdIn._

object Main extends App {

  def printScores(game: Game) = {
    game.players.sortBy(- _.score).zipWithIndex.foreach{
      case (player, index) =>
        println(s"${index + 1}. ${player.name} ${player.score}")
    }
  }

  def takeTurn(game: Game, player: Player): Player = {
    game.dice.resetDice()

    println(s"\n\n${player.name}'s turn:")
    val score = takeTurn(game.dice, 0, 1)

    lazy val playerHasStarted = player.score != 0
    lazy val playerCanStart =  score >= game.StartingScore

    if (playerHasStarted || playerCanStart) {
      println(s"${player.name} scored $score")
      player.copy(score = player.score + score)
    }
    else
      player
  }

  @tailrec
  private def takeTurn(dice: Dice, score : Int, roundNumber: Int, hasAttemptedSingleThrow: Boolean = false): Int = {
    dice.remainingDice.foreach(_.roll())

    println(s"\nYou rolled: ${game.dice.remainingDice.map(_.value).sorted.mkString(",")}")

    game.calculateScoringDice(game.dice.remainingDice, 0).zipWithIndex match {
      case Nil if dice.remainingDice.length == dice.dice.length =>
        println("Oh shit! -> No scoring Dice!")
        score + NoScoringDice.score

      case Nil if dice.remainingDice.length == 1 && !hasAttemptedSingleThrow =>
        println("You have one more chance to score on this die!")
        takeTurn(dice, score, roundNumber, hasAttemptedSingleThrow = true)

      case Nil         =>
        println("Zero score for this round")
        0

      case scoringDice =>
        println(s"Possible choices are: \n${formatScoringDice(scoringDice)}")
        val choices = readIntegers("Please enter your Choices: ", scoringDice)

        val diceChoices = choices.map(choice => scoringDice(choice)._1)
        val diceToRemove = diceChoices.flatMap(_.dice)
        val roundScore   = diceChoices.map(_.score).sum

        val newScore = if (dice.remainingDice.length == 1) {
          println("You scored with a single die - bonus points!")
          score + roundScore + 500 * roundNumber
        }
        else score + roundScore

        dice.useDice(diceToRemove)

        println(s"new score = $newScore")

        val newRoundNum = dice.remainingDice match {
          case Nil =>
            dice.resetDice()
            roundNumber + 1
          case _ =>
            roundNumber
        }

        if (readBool("Roll again? "))
          takeTurn(dice, newScore, newRoundNum)
        else {
          newScore
        }
    }
  }

  @tailrec
  def readBool(message: String): Boolean = {
    print(message)

    val msg = readLine()
    val yesAnswers = "yes" :: "y" :: Nil
    val noAnswers  = "no"  :: "n" :: Nil
    if (yesAnswers contains msg.toLowerCase)
      true
    else if (noAnswers contains msg.toLowerCase)
      false
    else
      readBool(message)
  }

  def readIntegers(message: String, validNumbers: List[(ScoringDice, Int)]): List[Int] = {
    var input = ""
    try {
      input = readLine(message)
      val choiceIndicies = input.split(",").toList.map(_.toInt)
      val choices = validNumbers.filter(choiceIndicies contains _._2)

      val chosenDice = choices.flatMap(_._1.dice)
      lazy val useAllDiceOnce = chosenDice.length == chosenDice.distinct.length
      if ((choiceIndicies diff validNumbers.map(_._2)).nonEmpty | !useAllDiceOnce) {
        readIntegers(message, validNumbers)
      }
      else
        choiceIndicies
    }
    catch {
      case _ : PatternSyntaxException =>
        readIntegers(message, validNumbers)
      case e : NumberFormatException =>
        if (input.isEmpty) {
          Nil
        }
        else
          readIntegers(message, validNumbers)
    }
  }

  private def formatScoringDice(dice: List[(ScoringDice, Int)]): String = {
    dice.map{case (die, index) => s"$index: $die"}.mkString("\n")
  }

  print(s"Welcome to Dicemeal, how many players? ")
  val numPlayers = readInt()

  val players = (1 to numPlayers).map{i =>
    print(s"player $i: ")
    readLine()
  }.map(name => Player(name)).toList

  var game = Game(players)

  print("Set the seed for this new game: ")
  val seed = readInt()
  DiceMeal.setSeed(seed)


  while (!game.gameIsOver) {
    val newPlayers = game.players.map(player => takeTurn(game, player))
    game = game.copy(players = newPlayers)
    printScores(game)
  }
  printScores(game)
}
