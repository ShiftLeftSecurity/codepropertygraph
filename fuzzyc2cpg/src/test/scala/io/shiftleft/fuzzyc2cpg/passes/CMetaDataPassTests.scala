package io.shiftleft.fuzzyc2cpg.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.fuzzyc2cpg.Defines
import io.shiftleft.semanticcpg.language._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.jdk.CollectionConverters._

class CMetaDataPassTests extends AnyWordSpec with Matchers {

  "MetaDataPass" should {
    val cpg = Cpg.emptyCpg
    new CMetaDataPass(cpg).createAndApply()

    "create exactly two nodes" in {
      cpg.graph.V.asScala.size shouldBe 2
    }

    "create no edges" in {
      cpg.graph.E.asScala.size shouldBe 0
    }

    "create a metadata node with correct language" in {
      cpg.metaData.language.l shouldBe List("C")
    }

    "create a '<global>' NamespaceBlock" in {
      cpg.namespaceBlock.name.l shouldBe List(Defines.globalNamespaceName)
    }

  }
}
