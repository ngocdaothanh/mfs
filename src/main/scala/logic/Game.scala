package logic

import scala.collection.mutable.ArrayBuffer
import akka.actor.ActorRef

object MoveResult extends Enumeration {
  type MoveResult = Value
  val Invalid, Valid, GameOver = Value
}

import MoveResult._

trait Game[MoveData] {
  val players  = ArrayBuffer.empty[Option[ActorRef]]
  var nextMover: ActorRef = null

  def move(userRef: ActorRef, moveData: MoveData): MoveResult
}
