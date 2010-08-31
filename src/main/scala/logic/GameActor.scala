package logic

import scala.collection.mutable.ArrayBuffer
import se.scalablesolutions.akka.actor.{Actor, ActorRef, FSM}

case class GameEnter(userRef: ActorRef)
case class GameLeave(userRef: ActorRef)
case class GameJoin(userRef: ActorRef)
case class GameUnjoin(userRef: ActorRef)
case class GameMove[MDT](userRef: ActorRef, position: MDT)
case class GameInspect()

object GameActor {
	val TIMEOUT = 60 * 1000
}

class GameActor[MoveData](stateData: Game[MoveData]) extends Actor with FSM[Game[MoveData]] {
	import MoveResult._
	import GameActor._

	val watchers = ArrayBuffer.empty[ActorRef]

	//----------------------------------------------------------------------------

  def initialState = State(NextState, notEnoughPlayers, stateData)

  def notEnoughPlayers: StateFunction = {
    case Event(GameEnter(userRef), stateData) =>
      userRef !
      watchers += userRef
      State(NextState, notEnoughPlayers, stateData)

    case Event(GameLeave(userRef), stateData) =>
      val i = watchers.indexOf(userRef)
      if (i >= 0)
      	watchers.remove(i)
      else
        stateData.players -= Some(userRef)

      State(NextState, notEnoughPlayers, stateData)

    case Event(GameJoin(userRef), stateData) =>
      watchers -= userRef
      stateData.players  += Some(userRef)

      if (stateData.players.length == 2) {
      	stateData.nextMover = stateData.players(0).get
      	State(NextState, playing, stateData, Option(TIMEOUT))
      } else
      	State(NextState, notEnoughPlayers, stateData)

    case Event(GameUnjoin(userRef), stateData) =>
      stateData.players -= Some(userRef)
      State(NextState, notEnoughPlayers, stateData)
  }

  def playing: StateFunction = {
  	case Event(GameEnter(userRef), stateData) =>
      watchers += userRef
      State(NextState, playing, stateData)

    case Event(GameLeave(userRef), stateData) =>
      val i1 = watchers.indexOf(userRef)
      if (i1 >= 0) {
      	watchers.remove(i1)
      	State(NextState, playing, stateData)
      } else {
      	val i2 = stateData.players.indexOf(Some(userRef))
        stateData.players(i2) = None
        State(NextState, gameOver, clearPlayers(stateData))
      }

  	case Event(GameMove(userRef, position: MoveData), stateData) =>
      stateData.move(userRef, position) match {
      	case Valid =>
      	  State(NextState, playing, stateData)

      	case Invalid =>
      	  State(NextState, playing, stateData)

      	case GameOver =>
      	  State(NextState, gameOver, clearPlayers(stateData))
      }

  	case Event(StateTimeout, stateData) =>
  	  State(NextState, gameOver, clearPlayers(stateData))
  }

  def gameOver: StateFunction = {
  	case Event(GameEnter(userRef), stateData) =>
      watchers += userRef
      State(NextState, gameOver, stateData)

    case Event(GameLeave(userRef), stateData) =>
      watchers -= userRef
      State(NextState, gameOver, stateData)

    case Event(GameJoin(userRef), stateData) =>
      watchers -= userRef
      stateData.players  += Some(userRef)
      State(NextState, notEnoughPlayers, stateData)
  }

  override def handleEvent: StateFunction = {
	  case Event(GameInspect, stateData) =>
		  println(stateData.toString)
      State(NextState, currentState.stateFunction, stateData, currentState.timeout)
	}

  //----------------------------------------------------------------------------

  private def clearPlayers(stateData: Game[MoveData]) = {
  	for (Some(p) <- stateData.players) watchers += p
  	stateData.players.clear
  	stateData
  }
}
