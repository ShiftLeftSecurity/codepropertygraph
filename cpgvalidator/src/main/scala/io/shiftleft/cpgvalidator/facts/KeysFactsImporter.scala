package io.shiftleft.cpgvalidator.facts

import io.shiftleft.codepropertygraph.schema.CpgSchema

class KeysFactsImporter extends FactsImporter {
  import FactConstructionClasses._

  val schema = CpgSchema.instance

  override def loadFacts: List[KeysFact] = {
    for {
      node <- schema.nodeTypes
      property <- node.properties
    } yield node.name withKey property.name hasCardinality Cardinality(property.cardinality.name)
  }.toList

}
