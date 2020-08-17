package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.codepropertygraph.generated.nodes

/**
  * A general data flow problem, formulated as in the Dragon Book, Second Edition
  * on page 626, with mild modifications. In particular, instead of allowing only
  * for the specification of a boundary, we allow initialization of IN and OUT.
  */
class DataFlowProblem[V](flowGraph: FlowGraph,
                         transferFunction: TransferFunction[V],
                         meet: V => V,
                         inOutInit: InOutInit[V],
                         forward: Boolean)

/**
  * In essence, the flow graph is the control flow graph, however, we can
  * compensate for small deviations from our generic control flow graph to
  * one that is better suited for solving data flow problems. In particular,
  * method parameters are not part of our normal control flow graph. By
  * defining successors and predecessors, we provide a wrapper that takes
  * care of these minor discrepancies.
  * */
trait FlowGraph {
  val entryNode: nodes.StoredNode
  val exitNode: nodes.StoredNode
  val succ: Map[nodes.StoredNode, List[nodes.StoredNode]]
  val pred: Map[nodes.StoredNode, List[nodes.StoredNode]]
}

/**
  * This is actually a function family consisting of one transfer
  * function for each node of the flow graph. Each function maps
  * from the analysis domain to the analysis domain, e.g., for
  * reaching definitions, sets of definitions are mapped to sets
  * of definitions.
  * */
trait TransferFunction[V] {
  def apply(n: nodes.StoredNode, x: V): V
}

/**
  * As a practical optimization, OUT[N] is often initialized to GEN[N]. Moreover,
  * we need a way of specifying boundary conditions such as OUT[ENTRY] = {}. We
  * achieve both by allowing the data flow problem to specify initializers for
  * IN and OUT.
  * */
trait InOutInit[V] {

  def initIn: Map[nodes.StoredNode, V]

  def initOut: Map[nodes.StoredNode, V]

}
