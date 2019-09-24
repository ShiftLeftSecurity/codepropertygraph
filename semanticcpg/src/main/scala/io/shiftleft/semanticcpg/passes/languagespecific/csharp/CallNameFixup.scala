package io.shiftleft.semanticcpg.passes.languagespecific.csharp

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.NodeKeyNames
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._

import scala.collection.JavaConverters._

class CallNameFixup(cpg: Cpg) extends CpgPass(cpg) {
  override def run(): Iterator[DiffGraph] = {
    cpg.call.toIterator().foreach { call =>
      val colonIndex = call.methodFullName.indexOf(":")
      if (colonIndex != -1) {

        val namePart = call.methodFullName.substring(0, colonIndex)

        val nameWithoutTypes = earseTypeInformation(namePart)

        val nameParts = nameWithoutTypes.split("\\.").toList

        val nameFromFullName = nameParts.last


        if (nameFromFullName != call.name) {
          call.property(NodeKeyNames.NAME, nameFromFullName)
        }
      }
    }

    Iterator.empty
  }

  private def earseTypeInformation(name: String): String = {

    val dstStringBuilder = StringBuilder.newBuilder

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
