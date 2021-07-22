package io.shiftleft.passes

class WorkItem[T](item: T, runOnPart: T => Iterator[DiffGraph]) {
  def run(): Iterator[DiffGraph] = {
    runOnPart(item)
  }
}

abstract class ParallelCpgPass[T](val keyPools: Option[Iterator[KeyPool]] = None)
    extends CpgPassBase {

  def init(): Unit = {}

  def partIterator: Iterator[T]

  def runOnPart(part: T): Iterator[DiffGraph]

  def workItemIterator: Iterator[WorkItem[T]] = {
    partIterator.map(new WorkItem(_, runOnPart))
  }

}
