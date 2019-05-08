package io.shiftleft.queryprimitives.dsl

object DslWriterImplicits {

  implicit def toGenericPipeMethods[PipeType[+_], ElemType](pipe: PipeType[ElemType])
  : GenericPipeMethods[PipeType, ElemType] = {
    new GenericPipeMethods(pipe)
  }
}
