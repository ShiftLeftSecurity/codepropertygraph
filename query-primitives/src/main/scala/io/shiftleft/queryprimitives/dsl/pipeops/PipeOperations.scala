package io.shiftleft.queryprimitives.dsl.pipeops

import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe
import io.shiftleft.queryprimitives.dsl.{Implicits, RealPipeBuilder}

import scala.collection.GenTraversableOnce
import scala.language.higherKinds

trait PipeOperations[PipeType[+_], ElemType] extends BasicPipeOperations[PipeType, ElemType] {
  // We cannot make PipeOperations covariant in ElemType because the pipe containing the
  // elements of ElemType is passed as parameter and not as instance parameter which breaks
  // common variance semantics/handling.
  def castElemType[NewElemType]: PipeOperations[PipeType, NewElemType] = {
    this.asInstanceOf[PipeOperations[PipeType, NewElemType]]
  }

  def repeat[DstType >: ElemType](pipe: PipeType[DstType],
                                  function: DstType => DstType,
                                  times: Int): PipeType[DstType] = {
    val pipeOps = castElemType[DstType]

    var currentPipe: PipeType[DstType] = pipe
    for (_ <- 0 until times) {
      currentPipe = pipeOps.map(currentPipe, function)
    }
    currentPipe
  }

  def repeatUntil[DstType >: ElemType](pipe: PipeType[DstType],
                                       function: DstType => DstType,
                                       until: DstType => Boolean): PipeType[DstType] = {
    // TODO implement
    pipe
  }

  def flatRepeat[DstType >: ElemType](pipe: PipeType[ElemType],
                                      function: DstType => GenTraversableOnce[DstType],
                                      times: Int): RealPipe[DstType] = {
    val pipeOps = Implicits.getRealPipeOps.castElemType[DstType]

    var currentPipe: RealPipe[DstType] = flatMap(pipe, function)
    for (_ <- 0 until times - 1) {
      currentPipe = pipeOps.flatMap(currentPipe, function)
    }
    currentPipe
  }

  def flatRepeatUntil[DstType >: ElemType](pipe: PipeType[ElemType],
                                           function: DstType => GenTraversableOnce[DstType],
                                           until: DstType => Boolean): RealPipe[DstType] = {
    var stack: List[DstType] = toList(pipe)
    var builder = new RealPipeBuilder[DstType]()

    while (stack.nonEmpty) {
      val elem = stack.head
      stack = stack.tail

      if (until(elem)) {
        builder += elem
      } else {
        stack = function(elem).toList ::: stack
      }
    }
    builder.result
  }
}
