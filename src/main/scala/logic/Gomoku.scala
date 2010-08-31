package logic

import se.scalablesolutions.akka.actor.{Actor, FSM}

object Gomoku {
  val SIZE = 15
}

class Gomoku extends Game[Int] {
	import MoveResult._
	import Gomoku._

  // Board is rectangle size x size
  // Values in board cells are indices of users, -1 for blank
	val board = new Array[Int](SIZE * SIZE)
	for (i <- 0 until SIZE * SIZE) board(i) = -1

	def move(userActor: UserActor, position: Int): MoveResult = {
		if (userActor != nextMover || position < 0 || position >= SIZE * SIZE || board(position) != -1)
			Invalid
	  else {
	  	val uidx = players.indexOf(Some(userActor))
	  	board(position) = uidx
	  	nextMover = players(1 - uidx).get
	  	Valid
	  }
	}

  override def toString = {
  	val s = new StringBuffer
  	for (r <- 0 until SIZE) {
  		for (c <- 0 until SIZE) {
  			val i = r * SIZE + c
  			val v = board(i)
  			if (v == -1) s.append("- ") else s.append(v + " ")
  		}
  		s.append("\n")
  	}
  	s.toString
  }
}
