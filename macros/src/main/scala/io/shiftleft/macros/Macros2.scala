package io.shiftleft.macros

import scala.language.experimental.macros
import scala.reflect.macros.whitebox

object Macros2 {
  def foo(): Int => String = macro fooImpl
  // def foo2(i: Int): String = macro fooImpl

  def fooImpl(c: whitebox.Context)(): c.Expr[Int => String] = {
    import c.universe._
    // val src = "val a: Int => String = { a => a.toString }"
    // c.Expr[Int => String](q"""
    //   new Function[Int, String] { def apply(i: Int) = i.toString}
    // """)
    c.Expr[Int => String](q"""
      {i: Int => i.toString}
    """)
  }

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal.Traversal

  def query2(): Cpg => Traversal[nodes.StoredNode] = macro query2Impl

  def query2Impl(c: whitebox.Context)(): c.Expr[Cpg => Traversal[nodes.StoredNode]] = {
    import c.universe._
    c.Expr[Cpg => Traversal[nodes.StoredNode]](q"""
      { cpg: io.shiftleft.codepropertygraph.Cpg => cpg.method.asInstanceOf[Traversal[nodes.StoredNode]] }
    """)
  }
}
