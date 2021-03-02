package io.shiftleft.semanticcpg

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, Languages, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge.EdgeType
import io.shiftleft.semanticcpg.language._

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
          nodes.NewMetaData().language(language).overlays(overlays).policyDirectories(policyDirs)
        )
      }
    }

    def withFile(filename: String): MockCpg =
      withCustom { (graph, _) =>
        graph.addNode(
          nodes.NewFile().name(filename)
        )
      }

    def withNamespace(name: String): MockCpg =
      withCustom { (graph, _) =>
        {
          val namespaceBlock = nodes.NewNamespaceBlock().name(name)
          val namespace = nodes.NewNamespace().name(name)
          graph.addNode(namespaceBlock)
          graph.addNode(namespace)
          graph.addEdge(namespaceBlock, namespace, EdgeTypes.REF)
        }
      }

    def withTypeDecl(name: String,
                     isExternal: Boolean = false,
                     inNamespace: Option[String] = None,
                     inFile: Option[String] = None): MockCpg =
      withCustom { (graph, _) =>
        {
          val typeNode = nodes.NewType().name(name)
          val typeDeclNode = nodes
            .NewTypeDecl()
            .name(name)
            .fullName(name)
            .isExternal(isExternal)

          val member = nodes.NewMember().name("amember")

          graph.addNode(typeDeclNode)
          graph.addNode(typeNode)
          graph.addNode(member)
          graph.addEdge(typeNode, typeDeclNode, EdgeTypes.REF)
          graph.addEdge(typeDeclNode, member, EdgeTypes.AST)

          if (inNamespace.isDefined) {
            val namespaceBlock = cpg.namespaceBlock(inNamespace.get).head
            graph.addEdge(namespaceBlock, typeDeclNode, EdgeTypes.AST)
          }
          if (inFile.isDefined) {
            val fileNode = cpg.file.name(inFile.get).head
            graph.addEdge(typeDeclNode, fileNode, EdgeTypes.SOURCE_FILE)
          }
        }
      }

    def withMethod(name: String,
                   external: Boolean = false,
                   inTypeDecl: Option[String] = None,
                   fileName: String = ""): MockCpg =
      withCustom { (graph, _) =>
        val retParam = nodes.NewMethodReturn().typeFullName("int")
        val param = nodes.NewMethodParameterIn().order(1).name("param1")
        val paramType = nodes.NewType().name("paramtype")
        val paramOut = nodes.NewMethodParameterOut().name("param1").order(1)
        val method =
          nodes.NewMethod().isExternal(external).name(name).fullName(name).signature("asignature").filename(fileName)
        val block = nodes.NewBlock().typeFullName("int")
        val modifier = nodes.NewModifier().modifierType("modifiertype")

        graph.addNode(method)
        graph.addNode(retParam)
        graph.addNode(param)
        graph.addNode(paramType)
        graph.addNode(paramOut)
        graph.addNode(block)
        graph.addNode(modifier)
        graph.addEdge(method, retParam, EdgeTypes.AST)
        graph.addEdge(method, param, EdgeTypes.AST)
        graph.addEdge(param, paramOut, EdgeTypes.PARAMETER_LINK)
        graph.addEdge(method, block, EdgeTypes.AST)
        graph.addEdge(param, paramType, EdgeTypes.EVAL_TYPE)
        graph.addEdge(paramOut, paramType, EdgeTypes.EVAL_TYPE)
        graph.addEdge(method, modifier, EdgeTypes.AST)

        if (inTypeDecl.isDefined) {
          val typeDeclNode = cpg.typeDecl.name(inTypeDecl.get).head
          graph.addEdge(typeDeclNode, method, EdgeTypes.AST)
        }
      }

    def withTagsOnMethod(methodName: String,
                         methodTags: List[(String, String)] = List(),
                         paramTags: List[(String, String)] = List()): MockCpg =
      withCustom { (graph, cpg) =>
        implicit val diffGraph: DiffGraph.Builder = graph
        methodTags.foreach {
          case (k, v) =>
            cpg.method.name(methodName).newTagNodePair(k, v).store()
        }
        paramTags.foreach {
          case (k, v) =>
            cpg.method.name(methodName).parameter.newTagNodePair(k, v).store()
        }
      }

    def withCallInMethod(methodName: String, callName: String): MockCpg =
      withCustom { (graph, cpg) =>
        val methodNode = cpg.method.name(methodName).head
        val blockNode = methodNode.block.head
        val callNode = nodes.NewCall().name(callName)
        graph.addNode(callNode)
        graph.addEdge(blockNode, callNode, EdgeTypes.AST)
        graph.addEdge(methodNode, callNode, EdgeTypes.CONTAINS)
      }

    def withLocalInMethod(methodName: String, localName: String): MockCpg =
      withCustom { (graph, cpg) =>
        val methodNode = cpg.method.name(methodName).head
        val blockNode = methodNode.block.head
        val typeNode = nodes.NewType().name("alocaltype")
        val localNode = nodes.NewLocal().name(localName).typeFullName("alocaltype")
        graph.addNode(localNode)
        graph.addNode(typeNode)
        graph.addEdge(blockNode, localNode, EdgeTypes.AST)
        graph.addEdge(localNode, typeNode, EdgeTypes.EVAL_TYPE)
      }

    def withLiteralArgument(callName: String, literalCode: String): MockCpg = {
      withCustom { (graph, cpg) =>
        val callNode = cpg.call.name(callName).head
        val methodNode = callNode.method.head
        val literalNode = nodes.NewLiteral().code(literalCode)
        val typeDecl = nodes
          .NewTypeDecl()
          .name("ATypeDecl")
          .fullName("ATypeDecl")

        graph.addNode(typeDecl)
        graph.addNode(literalNode)
        graph.addEdge(callNode, literalNode, EdgeTypes.AST)

        graph.addEdge(methodNode, literalNode, EdgeTypes.CONTAINS)
      }
    }

    def withIdentifierArgument(callName: String, name: String): MockCpg =
      withCustom { (graph, cpg) =>
        val callNode = cpg.call.name(callName).head
        val methodNode = callNode.method.head
        val identifierNode = nodes.NewIdentifier().name(name)
        val typeDecl = nodes.NewTypeDecl().name("abc")
        graph.addNode(identifierNode)
        graph.addNode(typeDecl)
        graph.addEdge(callNode, identifierNode, EdgeTypes.AST)
        graph.addEdge(methodNode, identifierNode, EdgeTypes.CONTAINS)
        graph.addEdge(identifierNode, typeDecl, EdgeTypes.REF)
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
