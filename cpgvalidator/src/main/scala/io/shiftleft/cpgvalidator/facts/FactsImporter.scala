package io.shiftleft.cpgvalidator.facts

import java.io.FileInputStream

import play.api.libs.json.{JsValue, Json}

abstract class FactsImporter {

  protected lazy val cpgJson: JsValue =
    Json.parse(
      new FileInputStream("codepropertygraph/src/main/resources/cpg.json")
    )

  def loadFacts: List[FactConstructionClasses.Fact]

}
