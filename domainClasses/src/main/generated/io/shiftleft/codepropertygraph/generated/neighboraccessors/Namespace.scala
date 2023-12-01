
package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.Language.*

final class AccessNeighborsForNamespace(val node: nodes.Namespace) extends AnyVal {
  /** 
 * Traverse to NAMESPACE_BLOCK via REF IN edge. */
def namespaceBlockViaRefIn: Iterator[nodes.NamespaceBlock] = refIn.collectAll[nodes.NamespaceBlock]


def refIn: Iterator[nodes.NamespaceBlock] = node._refIn.cast[nodes.NamespaceBlock]
}

final class AccessNeighborsForNamespaceTraversal(val traversal: Iterator[nodes.Namespace]) extends AnyVal {
  /** 
 * Traverse to NAMESPACE_BLOCK via REF IN edge. */
def namespaceBlockViaRefIn: Iterator[nodes.NamespaceBlock] = traversal.flatMap(_.namespaceBlockViaRefIn)


def refIn: Iterator[nodes.NamespaceBlock] = traversal.flatMap(_.refIn)
}

