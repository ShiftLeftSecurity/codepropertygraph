package io.shiftleft.semanticcpg.passes.trim

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.{SimpleCpgPassV2, DiffGraph}
import io.shiftleft.semanticcpg.language._
import org.slf4j.{Logger, LoggerFactory}
import overflowdb._

class TrimPass(cpg: Cpg) extends SimpleCpgPassV2 {
  override def run(): Iterator[DiffGraph] = {
    val reduction = cpg.all.iterator
      .map(_.asInstanceOf[NodeRef[NodeDb]].get.trim())
      .sum
    val oldSize = reduction >>> 32
    val newSize = reduction & 0x00000000ffffffffL
    TrimPass.logger.debug(
      s"Trim pass: reduced number of refs from ${oldSize} to ${newSize}, i.e. by factor of ${1.0 - newSize.toFloat / oldSize.toFloat}")
    Iterator.empty[DiffGraph]
  }
}

object TrimPass {
  private val logger: Logger = LoggerFactory.getLogger(classOf[TrimPass])
}
