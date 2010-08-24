package logic

import se.scalablesolutions.akka.actor.{Actor, FSM}

import scala.collection.mutable.ArrayBuffer

class User {}

case class Enter(user: User)
case class Leave(user: User)
case class Join(user: User)
case class Unjoin(user: User)
case class Move(user: User, position: Int)

class StateData {
  var board = Array(0, 0, 0,
                    0, 0, 0,
                    0, 0, 0)
  var players  = ArrayBuffer[User]()
  var watchers = ArrayBuffer[User]()
}

object Lock {
  val TIMEOUT = 5000
}

class Tictactoe extends Actor with FSM[StateData] {
  def initialState = State(NextState, notEnoughPlayers, new StateData)

  def notEnoughPlayers: StateFunction = {
    case Event(Enter(user), stateData) =>
      stateData.watchers += user
      State(NextState, notEnoughPlayers, stateData)

    case Event(Leave(user), stateData) =>
      if (stateData)
  }

  def redTurn: StateFunction = {
  }

  def blackTurn: StateFunction = {
  }

  def gameOver: StateFunction = {
  }
}
