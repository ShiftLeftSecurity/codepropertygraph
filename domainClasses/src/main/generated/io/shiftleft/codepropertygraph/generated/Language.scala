package io.shiftleft.codepropertygraph.generated

/** combining all implicits into one trait that can be mixed in further downstream */
trait Language
    extends accessors.ConcreteStoredConversions
    with traversals.ConcreteStoredConversions
    with neighboraccessors.Conversions
    with flatgraph.traversal.Language
    with flatgraph.Implicits {
  implicit def cpgToGeneratedNodeStarters(cpg: Cpg): CpgNodeStarters = CpgNodeStarters(cpg)
}

object Language extends Language
