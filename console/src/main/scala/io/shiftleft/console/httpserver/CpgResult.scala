package io.shiftleft.console.httpserver

sealed trait CpgOperationResult[+T]

final case class CpgOperationSuccess[T](result: T) extends CpgOperationResult[T]

final case class CpgOperationFailure(ex: Throwable) extends CpgOperationResult[Nothing]
