package io.shiftleft.semanticcpg

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, Languages, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge.EdgeType
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._

package object testing {

  object MockCpg {

    def apply(): MockCpg = new MockCpg

    def apply(f: (DiffGraph.Builder, Cpg) => Unit): MockCpg = new MockCpg().withCustom(f)

  }

  case class MockCpg(cpg: Cpg = Cpg.emptyCpg) {

    def withMetaData(language: String = Languages.C): MockCpg = withMetaData(language, List(), List())

    def withMetaData(language: String, overlays: List[String], policyDirs: List[String]): MockCpg = {
      withCustom { (diffGraph, _) =>
        diffGraph.addNode(
          nodes.NewMetaData(language = language, overlays = overlays, policyDirectories = policyDirs)
        )
      }
    }

    def withMethod(name: String, external: Boolean = false, inTypeDecl: Boolean = true): MockCpg =
      withCustom { (graph, _) =>
        val retParam = nodes.NewMethodReturn()
        val param = nodes.NewMethodParameterIn(order = 1, name = "param1")
        val paramOut = nodes.NewMethodParameterOut(order = 1)
        val method = nodes.NewMethod(isExternal = external, name = name)
        val clazz = nodes.NewTypeDecl(name = "AClass", fullName = "AClass", isExternal = external)
        val block = nodes.NewBlock()

        graph.addNode(method)
        graph.addNode(retParam)
        graph.addNode(param)
        graph.addNode(paramOut)
        graph.addNode(block)
        graph.addEdge(method, retParam, EdgeTypes.AST)
        graph.addEdge(method, param, EdgeTypes.AST)
        graph.addEdge(param, paramOut, EdgeTypes.PARAMETER_LINK)
        graph.addEdge(method, block, EdgeTypes.AST)

        if (inTypeDecl) {
          graph.addNode(clazz)
          graph.addEdge(clazz, method, EdgeTypes.AST)
        }
      }

    def withTagsOnMethod(methodName: String,
                         methodTags: List[(String, String)] = List(),
                         paramTags: List[(String, String)] = List()): MockCpg =
      withCustom { (graph, cpg) =>
        implicit val diffGraph: DiffGraph.Builder = graph
        methodTags.foreach {
          case (k, v) =>
            cpg.method.name(methodName).newTagNodePair(k, v).store
        }
        paramTags.foreach {
          case (k, v) =>
            cpg.method.name(methodName).parameter.newTagNodePair(k, v).store
        }
      }

    def withLiteralInMethod(methodName: String, literalCode: String): MockCpg = {
      withCustom { (graph, cpg) =>
        val methodNode = cpg.method.head
        val blockNode = methodNode.start.block.head
        val callNode = nodes.NewCall()
        val literalNode = nodes.NewLiteral(code = literalCode)
        graph.addNode(callNode)
        graph.addNode(literalNode)
        graph.addEdge(blockNode, callNode, EdgeTypes.AST)
        graph.addEdge(callNode, literalNode, EdgeType.AST.toString)
        graph.addEdge(methodNode, literalNode, EdgeTypes.CONTAINS)
      }
    }

    def withCustom(f: (DiffGraph.Builder, Cpg) => Unit): MockCpg = {
      val diffGraph = new DiffGraph.Builder
      f(diffGraph, cpg)
      class MyPass extends CpgPass(cpg) {
        override def run(): Iterator[DiffGraph] = {
          Iterator(diffGraph.build())
        }
      }
      new MyPass().createAndApply()
      this
    }
  }

}
