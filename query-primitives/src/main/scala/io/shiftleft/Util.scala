package io.shiftleft

class IdentityHashCode(val value: Int) extends AnyVal

object IdentityHashCode {
  def apply(instance: Any) = new IdentityHashCode(System.identityHashCode(instance))
}
