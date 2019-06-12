package io.shiftleft.passes.containsedges

import gremlin.scala._
import io.shiftleft.TinkerGraphTestInstance
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import org.scalatest.{Matchers, WordSpec}

class ContainsEdgePassTest extends WordSpec with Matchers {

  trait Fixture {
    implicit val graph: ScalaGraph = TinkerGraphTestInstance.create

    val fileVertex = graph + NodeTypes.FILE
    val typeDeclVertex = graph + NodeTypes.TYPE_DECL
    val typeMethodVertex = graph + NodeTypes.METHOD
    val methodVertex = graph + NodeTypes.METHOD
    val innerMethodVertex = graph + NodeTypes.METHOD
    val expressionVertex = graph + NodeTypes.CALL
    val innerExpressionVertex = graph + NodeTypes.CALL

    fileVertex --- EdgeTypes.AST --> typeDeclVertex
    typeDeclVertex --- EdgeTypes.AST --> typeMethodVertex

    fileVertex --- EdgeTypes.AST --> methodVertex
    methodVertex --- EdgeTypes.AST --> innerMethodVertex
    methodVertex --- EdgeTypes.AST --> expressionVertex
    innerMethodVertex --- EdgeTypes.AST --> innerExpressionVertex

    val containsEdgeCalculator = new ContainsEdgePass(graph)
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
