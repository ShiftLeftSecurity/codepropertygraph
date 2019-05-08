package io.shiftleft.queryprimitives.dsl.pipeops

import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe
import io.shiftleft.queryprimitives.dsl.{Implicits, RealPipeBuilder}

import scala.collection.GenTraversableOnce
import scala.language.higherKinds

trait PipeOperations[PipeType[+_]] extends BasicPipeOperations[PipeType] {

  private[dsl] def repeat[DstType >: ElemType, ElemType]
  (pipe: PipeType[ElemType],
   function: DstType => DstType,
   times: Int): PipeType[DstType] = {
    var currentPipe: PipeType[DstType] = pipe
    for (_ <- 0 until times) {
      currentPipe = map(currentPipe, function)
    }
    currentPipe
  }

  /*
  def repeat[DstType >: ElemType, ElemType](pipe: PipeType[ElemType],
                                  function: DstType => DstType,
                                  times: Int,
                                  emitFunction: DstType => Boolean): RealPipe[DstType] = {
    val pipeOps = castElemType[DstType]
    val realPipeOps = Implicits.getRealPipeOps.castElemType[DstType]

    var emitted = pipeOps.filter(pipe, emitFunction)
    var currentPipe: PipeType[DstType] = pipe
    for (_ <- 0 until times) {
      currentPipe = pipeOps.map(currentPipe, function)
      emitted = realPipeOps.append(emitted, pipeOps.filter(currentPipe, emitFunction))
    }
    currentPipe
  }
   */

  private[dsl] def repeatUntil[DstType >: ElemType, ElemType]
  (pipe: PipeType[ElemType],
   function: DstType => DstType,
   until: DstType => Boolean): PipeType[DstType] = {
    // TODO implement
    pipe
  }

  private[dsl] def flatRepeat[DstType >: ElemType, ElemType]
  (pipe: PipeType[ElemType],
   function: DstType => GenTraversableOnce[DstType],
   times: Int): RealPipe[DstType] = {
    val pipeOps = Implicits.realPipeOps

    var currentPipe: RealPipe[DstType] = flatMap(pipe, function)
    for (_ <- 0 until times - 1) {
      currentPipe = pipeOps.flatMap(currentPipe, function)
    }
    currentPipe
  }

  private[dsl] def flatRepeatUntil[DstType >: ElemType, ElemType]
  (pipe: PipeType[ElemType],
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
