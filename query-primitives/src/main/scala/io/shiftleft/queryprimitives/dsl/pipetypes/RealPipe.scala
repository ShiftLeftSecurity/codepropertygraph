package io.shiftleft.queryprimitives.dsl.pipetypes

import scala.language.higherKinds

object RealPipe {
  sealed abstract class RealPipeImpl {
    type PipeType[+ElemType]

    private[dsl] def apply[ElemType](impl: List[ElemType]): PipeType[ElemType]
    private[dsl] def unwrap[ElemType](pipeType: PipeType[ElemType]): List[ElemType]

    def empty[ElemType]: PipeType[ElemType]
  }

  val RealPipe: RealPipeImpl = new RealPipeImpl {
    override type PipeType[+ElemType] = List[ElemType]

    override def apply[ElemType](impl: List[ElemType]): List[ElemType] = {
      impl
    }

    override def unwrap[ElemType](pipeType: PipeType[ElemType]): List[ElemType] = {
      pipeType
    }

    override def empty[ElemType]: List[ElemType] = {
      Nil
    }
  }

  type RealPipe[+ElemType] = RealPipe.PipeType[ElemType]
}
