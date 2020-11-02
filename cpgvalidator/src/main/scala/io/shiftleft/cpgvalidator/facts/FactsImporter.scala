package io.shiftleft.cpgvalidator.facts

import java.io.FileInputStream

import play.api.libs.json.{JsValue, Json}

abstract class FactsImporter {

  protected lazy val cpgJson: JsValue =
    Json.parse(
      new FileInputStream("./schema/target/scala-2.13/src_managed/main/cpg.json")
    )

  def loadFacts: List[FactConstructionClasses.Fact]

}
