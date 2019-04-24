package io.shiftleft.queryprimitives.dsl

// We cannot make this any AnyVal because Scala currently has the limitation of
// no allowing value classes to wrap other value classes.
class RealPipeMethods[ElemType](val pipe: RealPipe[ElemType]) /* extends AnyVal */ {

  def map[DstType](function: ElemType => DstType)
                  (implicit ops: PipeOperations[RealPipe, ElemType]): RealPipe[DstType] = {
    ops.map(pipe, function)
  }

}
