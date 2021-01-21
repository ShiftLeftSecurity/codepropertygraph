package io.shiftleft.macros

import scala.language.experimental.macros
import scala.meta._
import scala.reflect.macros.whitebox

object MacrosSm extends App {

  val src = "val a: Int => String = { a => a.toString }"
  val parsed = src.parse[Stat].get

  // println(parsed.structure)
  // println(parsed.syntax)

  val reified = q"$parsed" // of type Defn.Val
  // println(reified.getClass) //scala.meta.Defn$Val$DefnValImpl
  // println(reified)
  // idea: return this in a macro?


  def foo(): Int => String = macro fooImpl

  def fooImpl(c: whitebox.Context)(): c.Expr[Int => String] = {
    // import c.universe._
    val src = "val a: Int => String = { a => a.toString }"
    val parsed = src.parse[Stat].get
    val reified = q"$parsed" // of type Defn.Val
    // ???
    c.Expr[Int => String](reified.asInstanceOf[scala.meta.Defn.Val])
  }

}

