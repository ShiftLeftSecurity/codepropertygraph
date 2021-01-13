package io.shiftleft.macros

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import scala.util.matching._

object PrintTree {

  def printExact(expr: Any): Unit =
  macro printExactMacro

  def printExactMacro(c: Context)(expr: c.Tree) = {
    import c.universe._

    val fileContent = new String(expr.pos.source.content)
    val start = expr.pos.start
    val end = expr.pos.end

    q"""println("code: " + $fileContent + " start: " + $start.toString + " end: " + $end.toString)"""
  }

  def duplicateBlock(expr: Any): String  = macro duplicateBlockImpl

  def duplicateBlockImpl(c: Context)(expr: c.Tree) = {
    import c.universe._
    q"""$expr """
  }
}

