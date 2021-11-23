package io.shiftleft.semanticcpg.langv2

import scala.collection.IterableOnceOps

class SingleMarker
class OptionMarker
class IterMarker[_CC[_], _C] {
  type CC[T] = _CC[T]
  type C = _C
}
