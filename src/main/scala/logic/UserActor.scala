package logic

import se.scalablesolutions.akka.actor.{Actor, ActorRef, FSM}

case class UserEnter(gameRef: ActorRef)
case class UserLeave
case class UserJoin
case class UserMove

object UserActor {
	def enterGame(userRef: ActorRef, gameRef: ActorRef) {
		gameRef ! GameEnter(userRef)
	}
}

class UserActor extends Actor with FSM[Game[AnyRef]] {
  def initialState = State(NextState, inLobby, null)

  def inLobby: StateFunction = {
  	case Event(UserEnter(gameRef), stateData) =>

  		State(NextState, watching, stateData)
  }

  // In a room

  def watching: StateFunction = {

  }

  def joining: StateFunction = {

  }

  def playing: StateFunction = {

  }
}

class ConsoleUser extends User {

}

