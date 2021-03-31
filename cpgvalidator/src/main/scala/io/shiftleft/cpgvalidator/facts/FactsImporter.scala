package io.shiftleft.cpgvalidator.facts

abstract class FactsImporter {
  def loadFacts: List[FactConstructionClasses.Fact]
}
