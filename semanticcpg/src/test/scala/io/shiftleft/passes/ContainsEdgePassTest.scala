package io.shiftleft.passes.containsedges

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.TinkerGraphTestInstance
import org.scalatest.{Matchers, WordSpec}

class ContainsEdgePassTest extends WordSpec with Matchers {

  import ContainsEdgePassTest.Fixture

  "Files " can {
    "contain Methods" in Fixture { fixture =>
      fixture.methodVertex.in(EdgeTypes.CONTAINS).toList should contain only fixture.fileVertex
    }
    "contain Classes" in Fixture { fixture =>
      fixture.typeDeclVertex.in(EdgeTypes.CONTAINS).toList should contain only fixture.fileVertex
    }
  }

  "Classes " can {
    "contain Methods" in Fixture { fixture =>
      fixture.typeMethodVertex.in(EdgeTypes.CONTAINS).toList should contain only fixture.typeDeclVertex
    }
  }

  "Methods " can {
    "contain Methods" in Fixture { fixture =>
      fixture.innerMethodVertex.in(EdgeTypes.CONTAINS).toList should contain only fixture.methodVertex
    }
    "contain expressions" in Fixture { fixture =>
      fixture.expressionVertex.in(EdgeTypes.CONTAINS).toList should contain only fixture.methodVertex
      fixture.innerExpressionVertex.in(EdgeTypes.CONTAINS).toList should contain only fixture.innerMethodVertex
    }
  }

}

object ContainsEdgePassTest {
  private class Fixture {
    private implicit val graph: ScalaGraph = TinkerGraphTestInstance.create

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

  private object Fixture {
    def apply[T](fun: Fixture => T): T = {
      val fixture = new Fixture()
      try fun(fixture) finally fixture.graph.close()
    }
  }
}
