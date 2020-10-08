package io.shiftleft.semanticcpg.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.passes.{DiffGraph, IntervalKeyPool, ParallelCpgPass}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.cfgcreation.CfgCreator

/**
  * A pass that creates control flow graphs from abstract syntax trees.
  *
  * Control flow graphs can be calculated independently per method.
  * Therefore, we inherit from `ParallelCpgPass`. As for other
  * parallel passes, we provide a key pool that is split into equal
  * parts, each of which is assigned to exactly one method prior
  * to branching off into parallel computation. This ensures id
  * stability over multiple runs.
  *
  * Note: the version of OverflowDB that we currently use as a
  * storage backend does not assign ids to edges and this pass
  * only creates edges at the moment. Therefore, we could do
  * without key pools, however, this would not only deviate
  * from the standard template for parallel CPG passes but it
  * is also likely to bite us later, whenever we find that
  * adding nodes in this pass or adding edge ids to the
  * backend becomes necessary.
  * */
class CfgCreationPass(cpg: Cpg, keyPool: IntervalKeyPool)
    extends ParallelCpgPass[nodes.Method](cpg, keyPools = Some(keyPool.split(cpg.method.size))) {

  override def partIterator: Iterator[nodes.Method] = cpg.method.iterator

  override def runOnPart(method: nodes.Method): Iterator[DiffGraph] =
    new CfgCreator(method).run()

}
