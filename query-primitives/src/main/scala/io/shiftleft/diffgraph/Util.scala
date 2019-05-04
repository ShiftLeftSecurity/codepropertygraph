package io.shiftleft.diffgraph

case class IdentityHashWrapper[T <: AnyRef](value: T) {
  override def hashCode(): Int = {
    System.identityHashCode(value)
  }

  override def equals(other: Any): Boolean = {
    if (other == null) {
      false
    } else if (!other.isInstanceOf[IdentityHashWrapper[T]]) {
      false
    } else {
      this.value eq other.asInstanceOf[IdentityHashWrapper[T]].value
    }
  }
}
