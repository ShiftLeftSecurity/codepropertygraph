package io.shiftleft.cpgvalidator.facts

import play.api.libs.json.{JsValue, Json}

import java.io.FileInputStream

abstract class FactsImporter {

  protected lazy val cpgJson: JsValue =
    Json.parse(
      new FileInputStream("./schema/target/scala-2.13/src_managed/main/cpg.json")
    )

  def loadFacts: List[FactConstructionClasses.Fact]

}
