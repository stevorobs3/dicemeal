case class Game(
  players: List[Player]
) {
  val MaxScore = 10000
  val StartingScore = 500
  def gameIsOver: Boolean = {
    players.exists(_.score >= MaxScore)
  }

  def winner: Option[Player] = {
    players
      .filter(_.score <= MaxScore)
      .sortBy(- _.score)
      .headOption
  }

  def canStart(player: Player): Boolean = {
    player.score >= StartingScore
  }

  def calculateScoringDice(diceRolls: List[Die], roundNumber: Int):List[ScoringDice] = {
    val fiveRolls     = diceRolls.filter(_.value == 5).map(_ => SingleFive)
    val oneRolls      = diceRolls.filter(_.value == 1).map(_ => SingleOne)
    val threeOfAKinds = calculateNOfAKinds(diceRolls, 3)
    val fourOfAKinds  = calculateNOfAKinds(diceRolls, 4)
    val fiveOfAKinds  = calculateNOfAKinds(diceRolls, 5)
    val sixOfAKinds   = calculateNOfAKinds(diceRolls, 6)
    val straights     = List(Straight).filter(_ => (diceRolls.map(_.value) diff (1 to 6)) == Nil)
    val threePairs    = List(ThreePairs(diceRolls.distinct)).filter(_ => hasThreePairs(diceRolls))

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

  private def calculateNOfAKinds(diceRolls: List[Die], n: Int) = {
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
          nOfAKind(die.head)
      }
      .toList
  }

  /*
   * points:
   * 5's = 50
   * 1's = 100
   * 3 * 1's = 1000
   * 3 of a kind = 100 * dice rolled
   * 4,5,6(n) of a kind = (3 of a kind) * 2^(n-3)
   * straight 1 - 6 = 1500
   * 3 pairs = 500 points (have to be unique)
   * no score on 6 dice = -3000
   * scoring on a single dice = roundNumber * 500
   */
}