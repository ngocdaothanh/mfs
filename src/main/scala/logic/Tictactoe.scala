package logic

import actor.Fsm
import se.scalablesolutions.akka.actor.Actor

case class Button(digit: Char)

class User {}

class StateData {
  val board = Array(0, 0, 0,
                    0, 0, 0,
                    0, 0, 0)
  val users = Array[User]()
}

object Lock {
  val TIMEOUT = 5000
}
/*
class Tictactoe extends Actor with Fsm[StateData] {
  def initialState = State(NextState, noUser, new StateData)

  def noUser: StateFunction = {
    case Event(Button(digit), sofar) =>
      val sofar2 = sofar + digit
      if (sofar2 == code) {
        println("Opened")
        State(NextState, open, "", Some(Lock.TIMEOUT))
      } else {
        if (sofar2.length < code.length) {
          println(sofar2)
          State(NextState, locked, sofar2)
        } else {
          println("Wrong code")
          initialState
        }
      }
  }

  def open: StateFunction = {
    case Event(_, _) =>
      println("Locked")
      initialState
  }
}
*/