package io.shiftleft.cpgvalidator.facts

import better.files.File
import io.shiftleft.utils.ProjectRoot
import play.api.libs.json.{JsValue, Json}

abstract class FactsImporter {

  protected lazy val cpgJson: JsValue =
    File(ProjectRoot.relativise("schema/target/scala-2.13/src_managed/main/cpg.json")).fileInputStream
      .map(Json.parse)
      .get()

  def loadFacts: List[FactConstructionClasses.Fact]

}
