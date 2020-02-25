package io.shiftleft.semanticcpg.passes.languagespecific.fuzzyc

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{NodeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph, ParallelIteratorExecutor}
import io.shiftleft.semanticcpg.language._

/**
  * This pass has no other pass as prerequisite.
  * For each `TYPE` node that does not have a corresponding `TYPE_DECL`
  * node, this pass creates a `TYPE_DECL` node. The `TYPE_DECL` is
  * considered external.
  */
class TypeDeclStubCreator(cpg: Cpg) extends CpgPass(cpg) {

  private var typeDeclFullNameToNode = Map[String, nodes.TypeDeclBase]()

  override def run(): Iterator[DiffGraph] = {

    init()

    val typeNodeIterator = cpg.graph.V.hasLabel(NodeTypes.TYPE).toIterator()

    new ParallelIteratorExecutor[Vertex](typeNodeIterator).map { node =>
      val typ = node.asInstanceOf[nodes.Type]
      implicit val dstGraph: DiffGraph.Builder = DiffGraph.newBuilder
      typeDeclFullNameToNode.get(typ.fullName) match {
        case Some(_) =>
        case None =>
          val newTypeDecl = createTypeDeclStub(typ.name, typ.fullName)
          typeDeclFullNameToNode += typ.fullName -> newTypeDecl
          dstGraph.addNode(newTypeDecl)
      }
      dstGraph.build()
    }
  }

  private def createTypeDeclStub(name: String, fullName: String): nodes.NewTypeDecl = {
    new nodes.NewTypeDecl(
      name,
      fullName,
      isExternal = true,
      inheritsFromTypeFullName = Nil,
      NodeTypes.NAMESPACE_BLOCK,
      "<global>"
    )
  }

  private def init(): Unit = {
    cpg.typeDecl.sideEffect { typeDecl =>
      typeDeclFullNameToNode += typeDecl.fullName -> typeDecl
    }.exec
  }
}
