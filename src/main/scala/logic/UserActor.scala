package logic

import akka.actor.{Actor, ActorRef, FSM}

case class UserEnter(gameRef: ActorRef)
case class UserLeave()
case class UserJoin()
case class UserMove()

object UserActor {
  def enterGame(userRef: ActorRef, gameRef: ActorRef) {
    gameRef ! GameEnter(userRef)
  }
}

class UserActor extends Actor with FSM[String, Game[AnyRef]] {

}
