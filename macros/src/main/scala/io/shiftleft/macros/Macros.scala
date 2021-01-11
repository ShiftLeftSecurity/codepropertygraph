package io.shiftleft.macros

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import scala.util.matching._

object PrintTree {

  def printExact(expr: Any): Unit =
  macro printExactMacro

  def printExactMacro(c: Context)(expr: c.Tree) = {
    import c.universe._
    val code = showCode(expr)
    q"""println("code: " + $code)"""
  }
}

