package io.shiftleft.queries

import java.io.File

import io.shiftleft.dataflowengine.language.DataFlowFileToCpgFixture
import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.dataflowengine.language._

class LinuxExamples extends WordSpec with Matchers {

  val file = new File("queries/src/test/resources/aacraid/")

  DataFlowFileToCpgFixture(file) { cpg =>
    "find data flows from identifiers to args" in {
      file.exists shouldBe true
      cpg.identifier

      val sinkArguments = cpg.method.name("copy_from_user").parameter.argument
      sinkArguments.reachableBy(cpg.identifier).l.size should be > 0
    }
  }

}
