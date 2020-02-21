package io.shiftleft.semanticcpg.passes.trim
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.overflowdb.{NodeRef, OdbNode}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import org.apache.logging.log4j.{LogManager, Logger}
import io.shiftleft.semanticcpg.language._

// TODO this pass is problematic for two reasons: first, it writes into
// the graph directly without going via the DiffGraph, and it seems there
// is no other way of doing this. Second, it iterates over all nodes and
// processes them in a single thread. On Linux kernel drivers, the pass
// takes about 5 minutes to execute on an Intel i9-7900X CPU @ 3.30GHz.

class TrimPass(cpg: Cpg) extends CpgPass(cpg) {
  override def run(): Iterator[DiffGraph] = {
    val reduction = cpg.all
      .toStream()
      .mapToLong {
        _.asInstanceOf[io.shiftleft.overflowdb.NodeRef[OdbNode]].get().trim()
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
  private val logger: Logger = LogManager.getLogger(classOf[TrimPass])
}
