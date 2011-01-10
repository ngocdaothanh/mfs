package logic

import scala.collection.mutable.ArrayBuffer
import akka.actor.{Actor, ActorRef, FSM}

case class GameEnter(userRef: ActorRef)
case class GameLeave(userRef: ActorRef)
case class GameJoin(userRef: ActorRef)
case class GameUnjoin(userRef: ActorRef)
case class GameMove[MDT](userRef: ActorRef, position: MDT)
case class GameInspect()

object GameActor {
  val TIMEOUT = 60 * 1000
}

class GameActor[MoveData](stateData: Game[MoveData]) extends Actor with FSM[String, Game[MoveData]] {

}
