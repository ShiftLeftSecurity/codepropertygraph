package io.shiftleft.passes
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.CpgPassBase.logger
import org.slf4j.MDC

import scala.collection.mutable
import java.util.function.{BiConsumer, Supplier}
import scala.collection.immutable.ArraySeq

abstract class ParallelCpgPass[T](cpg: Cpg, outName: String = "", keyPools: Option[Iterator[KeyPool]] = None)
    extends CpgPassBase {

  def init(): Unit = {}

  //ParallelCpgPass eagerly drains all output before writing -- beware if a pass generates too much output for the diffgraph in-memory represssentation
  def partIterator: Iterator[T]

  def runOnPart(part: T): Iterator[DiffGraph]

  override def createAndApply(): Unit = createApplySerializeAndStore(null)

  private def genDiff(): (Int, DiffGraph) = {
    //scalac tries to be too clever about boxing, hence this dance
    val parts = partIterator.to(ArraySeq.untagged).unsafeArray.asInstanceOf[Array[Object]]
    val nParts = parts.size

    val res = java.util.Arrays
      .stream(parts)
      .parallel()
      .collect(
        new Supplier[mutable.ArrayBuffer[DiffGraph.Change]] {
          override def get(): mutable.ArrayBuffer[DiffGraph.Change] = mutable.ArrayBuffer[DiffGraph.Change]()
        },
        new BiConsumer[mutable.ArrayBuffer[DiffGraph.Change], Any] {
          override def accept(acc: mutable.ArrayBuffer[DiffGraph.Change], elem: Any): Unit = {
            for (diffgraph <- runOnPart(elem.asInstanceOf[T]);
                 elem <- diffgraph.iterator) { acc.addOne(elem) }
          }
        },
        new BiConsumer[mutable.ArrayBuffer[DiffGraph.Change], mutable.ArrayBuffer[DiffGraph.Change]] {
          override def accept(left: mutable.ArrayBuffer[DiffGraph.Change],
                              right: mutable.ArrayBuffer[DiffGraph.Change]): Unit = {
            left.appendAll(right.iterator)
          }
        }
      )
      .toArray

    (nParts, DiffGraph.ArrayChangeSet(res))

  }

  override def createApplySerializeAndStore(serializedCpg: SerializedCpg,
                                            inverse: Boolean = false,
                                            prefix: String = ""): Unit = {
    logger.info(s"Start of enhancement: ${name}")
    val tic = System.nanoTime
    //diagnostics
    var _nParts = 0
    var _nDiffElements = 0
    var _nDiffElementsExpanded = 0
    var _nanosApplier = 0L
    var _nanosSerialize = 0L
    var _nanosStore = 0L
    try {
      init()
      val (nParts, diff) = genDiff()
      _nDiffElements = diff.size
      _nParts = nParts
      val tocGenerated = System.nanoTime()

      val pool = keyPools match {
        case Some(pools) if pools.hasNext => Some(pools.next())
        case _                            => None
      }
      val applier =
        new DiffGraph.Applier(diff, cpg.graph, serializedCpg != null && !serializedCpg.isEmpty && inverse, pool)
      val applied = applier.run()
      _nDiffElementsExpanded = applied.size
      val tocApplied = System.nanoTime()
      _nanosApplier = tocApplied - tocGenerated
      if (serializedCpg != null && !serializedCpg.isEmpty) {
        val serialized = serialize(applied, inverse)
        val tocSerialized = System.nanoTime
        _nanosSerialize = tocSerialized - tocApplied
        store(serialized, generateOutFileName(prefix, outName, 0), serializedCpg)
        val tocStored = System.nanoTime
        _nanosStore = tocStored - tocSerialized
      }
    } catch {
      case exc: Exception =>
        logger.error(s"Enhancement ${name} failed!", exc)
        throw exc
    } finally {
      val toc = System.nanoTime
      if (serializedCpg == null || serializedCpg.isEmpty) {
        val fracApply = (_nanosApplier * 1e-2 / (1 + toc - tic))
        val fracRun = (100.0 - fracApply)
        MDC.put("time", s"${(toc - tic) * 1e-6}%.0f")
        logger.info(
          f"Enhancement $name completed in ${(toc - tic) * 1e-6}%.0f ms (split: ${fracRun}%2.0f%%/${fracApply}%2.0f%%). ${_nDiffElements}%d + ${_nDiffElementsExpanded - _nDiffElements}%d changes commited from ${_nParts}%d parts.")
        MDC.remove("time")
      } else {
        val fracApply = (_nanosApplier * 1e-2 / (1 + toc - tic))
        val fracSerialize = (_nanosSerialize * 1e-2 / (1 + toc - tic))
        val fracStore = (_nanosStore * 1e-2 / (1 + toc - tic))
        val fracRun = (100.0 - fracApply - fracSerialize - fracStore)
        MDC.put("time", s"${(toc - tic) * 1e-6}%.0f")
        logger.info(
          f"Enhancement $name completed in ${(toc - tic) * 1e-6}%.0f ms (split: ${fracRun}%2.0f%%/${fracApply}%2.0f%%/${fracSerialize}%2.0f%%/${fracStore}%2.0f%%${if (inverse) " including inverse"
          else ""}). ${_nDiffElements}%d + ${_nDiffElementsExpanded - _nDiffElements}%d changes committed from ${_nParts}%d parts.")
        MDC.remove("time")
      }
    }
  }

}
