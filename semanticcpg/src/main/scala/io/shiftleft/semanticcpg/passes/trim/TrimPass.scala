package io.shiftleft.semanticcpg.passes.trim

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import overflowdb.OdbNode

class TrimPass(cpg: Cpg) extends CpgPass(cpg) {
  override def run(): Iterator[DiffGraph] = {
    val reduction = cpg.all
      .toStream()
      .mapToLong {
        _.asInstanceOf[overflowdb.NodeRef[OdbNode]].get().trim()
      }
      .sum()
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
