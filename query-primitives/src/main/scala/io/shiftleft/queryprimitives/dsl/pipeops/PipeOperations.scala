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

  def repeat[SuperType >: ElemType](pipe: PipeType[SuperType],
                                    function: SuperType => SuperType,
                                    times: Int): PipeType[SuperType] = {
    val pipeOps = castElemType[SuperType]

    var currentPipe: PipeType[SuperType] = pipe
    for (_ <- 0 until times) {
      currentPipe = pipeOps.map(pipe, function)
    }
    currentPipe
  }

  def repeatUntil[SuperType >: ElemType](pipe: PipeType[SuperType],
                                         function: SuperType => SuperType,
                                         until: SuperType => Boolean): PipeType[SuperType] = {
    // TODO implement
    pipe
  }

  def flatRepeat[SuperType >: ElemType](pipe: PipeType[ElemType],
                                        function: SuperType => GenTraversableOnce[SuperType],
                                        times: Int): RealPipe[SuperType] = {
    val pipeOps = Implicits.getRealPipeOps.castElemType[SuperType]

    var currentPipe: RealPipe[SuperType] = flatMap(pipe, function)
    for (_ <- 0 until times - 1) {
      currentPipe = pipeOps.flatMap(currentPipe, function)
    }
    currentPipe
  }

  def flatRepeatUntil[SuperType >: ElemType](pipe: PipeType[ElemType],
                                             function: SuperType => GenTraversableOnce[SuperType],
                                             until: SuperType => Boolean): RealPipe[SuperType] = {
    var stack: List[SuperType] = toList(pipe)
    var builder = new RealPipeBuilder[SuperType]()

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
