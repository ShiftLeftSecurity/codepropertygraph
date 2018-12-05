package io.shiftleft.passes.containsedges

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
import org.scalatest.{Matchers, WordSpec}

class ContainsEdgePassTest extends WordSpec with Matchers {

  trait Fixture {
    implicit val cpg: ScalaGraph = TinkerGraph.open.asScala

    val fileVertex            = cpg + NodeTypes.FILE
    val typeDeclVertex        = cpg + NodeTypes.TYPE_DECL
    val typeMethodVertex      = cpg + NodeTypes.METHOD
    val methodVertex          = cpg + NodeTypes.METHOD
    val innerMethodVertex     = cpg + NodeTypes.METHOD
    val expressionVertex      = cpg + NodeTypes.CALL
    val innerExpressionVertex = cpg + NodeTypes.CALL

    fileVertex --- EdgeTypes.AST --> typeDeclVertex
    typeDeclVertex --- EdgeTypes.AST --> typeMethodVertex

    fileVertex --- EdgeTypes.AST --> methodVertex
    methodVertex --- EdgeTypes.AST --> innerMethodVertex
    methodVertex --- EdgeTypes.AST --> expressionVertex
    innerMethodVertex --- EdgeTypes.AST --> innerExpressionVertex

    val containsEdgeCalculator = new ContainsEdgePass(cpg)
    containsEdgeCalculator.executeAndApply()
  }

  "Files " can {
    "contain Methods" in new Fixture {
      methodVertex.in(EdgeTypes.CONTAINS).toList should contain only fileVertex
    }
    "contain Classes" in new Fixture {
      typeDeclVertex.in(EdgeTypes.CONTAINS).toList should contain only fileVertex
    }
  }

  "Classes " can {
    "contain Methods" in new Fixture {
      typeMethodVertex.in(EdgeTypes.CONTAINS).toList should contain only typeDeclVertex
    }
  }

  "Methods " can {
    "contain Methods" in new Fixture {
      innerMethodVertex.in(EdgeTypes.CONTAINS).toList should contain only methodVertex
    }
    "contain expressions" in new Fixture {
      expressionVertex.in(EdgeTypes.CONTAINS).toList should contain only methodVertex
      innerExpressionVertex.in(EdgeTypes.CONTAINS).toList should contain only innerMethodVertex
    }
  }

}
