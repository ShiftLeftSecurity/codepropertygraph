package io.shiftleft.codepropertygraph

import io.shiftleft.codepropertygraph.generated.nodes.*
import io.shiftleft.codepropertygraph.generated.Language.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class StepsTest extends AnyWordSpec with Matchers {

  ".help step" should {

    "show domain overview" in {
      val domainStartersHelp = Cpg.emptyCpg.help
      domainStartersHelp should include(".comment")
      domainStartersHelp should include("A source code comment")
    }

    "provide node-specific overview" in {
      val methodStepsHelp = Cpg.emptyCpg.method.help
      methodStepsHelp should include("Available steps for `Method`")

      val methodStepsHelpVerbose = Cpg.emptyCpg.method.helpVerbose
      methodStepsHelpVerbose should include("implemented in")
      methodStepsHelpVerbose should include("flatgraph.traversal.NodeLanguage")
    }

    "provide generic help" when {
      "using verbose mode" when {
        "traversing nodes" in {
          val methodTraversal = Iterator.empty[Method]
          methodTraversal.helpVerbose should include(".l")
          methodTraversal.helpVerbose should include(".label")
        }

        "traversing non-nodes" in {
          val stringTraversal = Iterator.empty[String]
          stringTraversal.helpVerbose should include(".l")
          stringTraversal.helpVerbose should not include ".label"
        }
      }
    }

  }

}
