package io.shiftleft.console.query

sealed trait CpgOperationResult[+T]

final case class CpgOperationSuccess[T <: AnyRef](result: T) extends CpgOperationResult[T]

final case class CpgOperationFailure(ex: Throwable) extends CpgOperationResult[Nothing]
