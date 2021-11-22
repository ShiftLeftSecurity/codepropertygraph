package io.shiftleft.semanticcpg.langv2

object Example {
  trait TypeMultiplexer {
    type IN[_]
    type OUT1[_]
    type OUT2[_]
  }

  abstract final class TypeGroup1 extends TypeMultiplexer {
    override type IN[T] = Option[T]
    override type OUT1[T] = Option[T]
    override type OUT2[T] = Iterable[T]
  }

  trait Ops[_TM <: TypeMultiplexer] {
    type TM = _TM
    def map[I, O](a: TM#IN[I])(f: I => O): TM#OUT1[O]
    def flatMap[I, O](a: TM#IN[I])(f: I => Iterator[O]): TM#OUT2[O]
  }

  implicit object TypeGroup1Ops extends Ops[TypeGroup1] {
    override def map[I, O](a: this.TM#IN[I])(f: I => O): this.TM#OUT1[O] = {
      a.map(f)
    }

    override def flatMap[I, O](a: this.TM#IN[I])(f: I => Iterator[O]): this.TM#OUT2[O] = {
      a.map(f.andThen(Iterable.from)).getOrElse(Iterable.empty)
    }
  }

  case class Dom1(x: Iterable[Dom2])
  case class Dom2()

  implicit def toSomeExtension(i: Option[Dom1]) = {
    new SomeExtension[TypeGroup1](i)
  }

  class SomeExtension[TM <: TypeMultiplexer](val a: TM#IN[Dom1]) extends AnyVal {
    def toFirstDom2(implicit ops: Ops[TM]) = {
      ops.map(a)(_.x.head)
    }
    def toAllDom2(implicit ops: Ops[TM]) = {
      ops.flatMap(a)(_.x.iterator)
    }
  }

  val d1 = Dom1(Dom2():: Dom2():: Nil)
  Option(d1).toFirstDom2

}
