package io.shiftleft.semanticcpg.passes.languagespecific.fuzzyc

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{NodeTypes, nodes}
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.language._

/**
  * This pass has no other pass as prerequisite.
  * For each `TYPE` node that does not have a corresponding `TYPE_DECL`
  * node, this pass creates a `TYPE_DECL` node. The `TYPE_DECL` is
  * considered external.
  */
class TypeDeclStubCreator(cpg: Cpg) extends ParallelCpgPass[nodes.Type](cpg) {

  private var typeDeclFullNameToNode = Map[String, nodes.TypeDeclBase]()

  override def init(): Unit = {
    cpg.typeDecl
      .sideEffect { typeDecl =>
        typeDeclFullNameToNode += typeDecl.fullName -> typeDecl
      }
      .l()
  }

  override def partIterator: Iterator[nodes.Type] = cpg.types.iterator

  override def runOnPart(typ: nodes.Type): Option[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder

    typeDeclFullNameToNode.get(typ.fullName) match {
      case Some(_) =>
      case None =>
        val newTypeDecl = createTypeDeclStub(typ.name, typ.fullName)
        typeDeclFullNameToNode += typ.fullName -> newTypeDecl
        dstGraph.addNode(newTypeDecl)
    }
    Some(dstGraph.build())
  }

  private def createTypeDeclStub(name: String, fullName: String): nodes.NewTypeDecl = {
    nodes.NewTypeDecl(
      name,
      fullName,
      isExternal = true,
      inheritsFromTypeFullName = Nil,
      NodeTypes.NAMESPACE_BLOCK,
      "<global>"
    )
  }

}
