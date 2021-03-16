package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb.traversal.Traversal

trait TrackingPointBase extends CpgNode

with WithinMethodBase

trait TrackingPoint extends StoredNode with TrackingPointBase
with WithinMethod


/** Traversal steps for TrackingPoint */
class TrackingPointTraversal[NodeType <: TrackingPoint](val traversal: Traversal[NodeType]) extends AnyVal {



}

