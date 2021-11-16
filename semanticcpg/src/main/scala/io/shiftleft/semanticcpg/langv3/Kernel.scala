package io.shiftleft.semanticcpg.langv3

import scala.collection.{IterableOps}

trait Kernel1To1[-I, +O] extends Function1[I, O]
trait Kernel1ToN[-I, +O] extends Function1[I, Iterator[O]]
trait Kernel1ToO[-I, +O] extends Function1[I, Option[O]]

object Helper {
 def apply[I, O](x: I, kernel: Kernel1To1[I, O]): O = {
  kernel.apply(x)
 }

 def apply[I, O](x: I, kernel: Kernel1ToN[I, O]): Iterator[O] = {
  kernel.apply(x)
 }

 def apply[I, O](x: I, kernel: Kernel1ToO[I, O]): Option[O] = {
  kernel.apply(x)
 }

 def apply[I, CC[_], C, O](x: IterableOps[I, CC, C], kernel: Kernel1To1[I, O]): CC[O] = {
  x.map(kernel)
 }

 def apply[I, CC[_], C, O](x: IterableOps[I, CC, C], kernel: Kernel1ToN[I, O]): CC[O] = {
  x.flatMap(kernel)
 }

 def apply[I, CC[_], C, O](x: IterableOps[I, CC, C], kernel: Kernel1ToO[I, O]): CC[O] = {
  x.flatMap(kernel)
 }

 def apply[I, O](x: Iterator[I], kernel: Kernel1To1[I, O]): Iterator[O] = {
  x.map(kernel)
 }

 def apply[I, O](x: Iterator[I], kernel: Kernel1ToN[I, O]): Iterator[O] = {
  x.flatMap(kernel)
 }

 def apply[I, O](x: Iterator[I], kernel: Kernel1ToO[I, O]): Iterator[O] = {
  x.flatMap(kernel)
 }

 def apply[I, O](x: Option[I], kernel: Kernel1To1[I, O]): Option[O] = {
  x.map(kernel)
 }

 def apply[I, O](x: Option[I], kernel: Kernel1ToN[I, O]): Iterable[O] = {
  x.map(y => Iterable.from(kernel(y))).getOrElse(Iterable.empty)
 }

 def apply[I, O](x: Option[I], kernel: Kernel1ToO[I, O]): Option[O] = {
  x.flatMap(kernel)
 }
}
