import scala.io.StdIn._

object Main extends App {

  def printScores(game: Game) = {
    game.players.sortBy(- _.score).zipWithIndex.foreach{
      case (player, index) =>
        println(s"${index + 1}. ${player.name} ${player.score}")
    }
  }

  def takeTurn(player: Player): Player = {
    player.copy(score = 10000)
  }

  println(s"Welcome to Dicemeal, how many players?")
  val numPlayers = readInt()

  val players = (1 to numPlayers).map{i =>
    println(s"player $i: ")
    readLine()
  }.map(name => Player(name)).toList

  var game = Game(players)

  while (!game.gameIsOver) {
    val newPlayers = game.players.map(player => takeTurn(player))
    game = game.copy(players = newPlayers)
  }
  printScores(game)
}