sealed trait ScoringDice{ val score: Int}

case object SingleFive extends ScoringDice{val score = 50}
case object SingleOne  extends ScoringDice{val score = 100}
case class  ThreeOfAKind(die : Die) extends ScoringDice{
  require(die.value >= 1 && die.value <= 6)
  val score =
    if (die.value == 1)
      1000
    else
      die.value * 100
}
case class FourOfAKind(die: Die) extends ScoringDice {
  val score = ThreeOfAKind(die).score*2
}

case class FiveOfAKind(die: Die) extends ScoringDice {
  val score = FourOfAKind(die).score*2
}
case class SixOfAKind(die: Die) extends ScoringDice {
  val score = FiveOfAKind(die).score*2
}

case object Straight extends ScoringDice{val score = 1500}

case class ThreePairs(pairs: List[Die]) extends ScoringDice{val score = 500}

case object NoScoringDice extends ScoringDice{val score = -3000}