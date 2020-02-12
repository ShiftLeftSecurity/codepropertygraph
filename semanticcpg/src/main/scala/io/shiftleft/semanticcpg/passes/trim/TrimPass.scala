package io.shiftleft.semanticcpg.passes.trim
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.overflowdb.{NodeRef, OdbNode}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import org.apache.logging.log4j.{LogManager, Logger}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.codepropertygraph.generated.nodes

class TrimPass(cpg: Cpg) extends CpgPass(cpg) {
  override def run():Iterator[DiffGraph] = {
    val reduction = cpg.all.toStream().mapToLong {
      _ match {
        case (onode: io.shiftleft.overflowdb.OdbNode) => onode.trim()
        case oref if oref.isInstanceOf[io.shiftleft.overflowdb.NodeRef[_]] => oref.asInstanceOf[io.shiftleft.overflowdb.NodeRef[OdbNode]].get().trim()
      }
    }.sum()
    val oldsz = reduction >> 32
    val newsz = reduction & 0x00000000ffffffffL
    TrimPass.logger.debug(s"Trim pass: reduced number of refs from ${oldsz} to ${newsz}, i.e. by factor of ${1.0 - newsz.toFloat / oldsz.toFloat}")
    Iterator.empty[DiffGraph]
  }
}

object TrimPass {
  private val logger: Logger = LogManager.getLogger(classOf[TrimPass])
}