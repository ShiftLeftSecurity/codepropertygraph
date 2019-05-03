package io.shiftleft.queryprimitives.dsl.pipetypes

object ShallowPipe {
  type ShallowPipe[+ElemType] = ElemType
}
