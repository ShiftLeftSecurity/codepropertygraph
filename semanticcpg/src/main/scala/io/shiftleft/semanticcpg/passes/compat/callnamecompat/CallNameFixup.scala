package io.shiftleft.semanticcpg.passes.compat.callnamecompat

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.NodeKeyNames
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._

/**
  * Compatibility pass which fixes mismatches between method full name and method name
  * properties.
  * TODO remove when not needed anymore.
  */
class CallNameFixup(cpg: Cpg) extends CpgPass(cpg) {
  override def run(): Iterator[DiffGraph] = {
    cpg.call.foreach { call =>
      if (call.methodFullName != null) {
        val colonIndex = call.methodFullName.indexOf(":")
        if (colonIndex != -1) {

          var qualMethodName = call.methodFullName.substring(0, colonIndex)

          // If the CALL name contains parametric type information, preserve it; otherwise, erase it.
          qualMethodName = if (call.name.indexOf("<") != -1) qualMethodName else eraseTypeInformation(qualMethodName)

          val unqualMethodName = qualMethodName.split("\\.").toList.last

          if (unqualMethodName != call.name) {
            call.setProperty(NodeKeyNames.NAME, unqualMethodName) // TODO: This should use diffgraph
          }
        }
      }
    }

    Iterator.empty
  }

  private def eraseTypeInformation(name: String): String = {
    val dstStringBuilder = new StringBuilder

    var openBracketCounter = 0
    for (i <- 0 until name.length) {
      val currentChar = name.charAt(i)
      if (currentChar == '<') {
        openBracketCounter += 1
      } else if (currentChar == '>') {
        openBracketCounter -= 1
      } else if (openBracketCounter == 0) {
        dstStringBuilder.append(currentChar)
      }
    }

    dstStringBuilder.toString()
  }
}
