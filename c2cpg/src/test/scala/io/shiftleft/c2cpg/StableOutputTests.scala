package io.shiftleft.c2cpg

import io.shiftleft.c2cpg.C2Cpg.Config
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.jdk.CollectionConverters._

class StableOutputTests extends AnyWordSpec with Matchers {

  private def createNodeStrings(): String = {
    val projectName = "stableid"
    val dirName = String.format("src/test/resources/testcode/%s", projectName)
    val c2cpg = new C2Cpg()
    val config = Config(includePaths = Set(dirName))
    val cpg = c2cpg.runAndOutput(config)
    val nodes = cpg.graph.V().asScala.toList
    nodes.sortBy(_.id()).map(x => x.label + ": " + x.propertiesMap().asScala.toString).mkString("\n")
  }

  "Nodes in test graph" should {
    "should be exactly the same on ten consecutive runs" in {
      List
        .range(0, 10)
        .map { _ =>
          createNodeStrings()
        }
        .distinct
        .size shouldBe 1
    }
  }

}
