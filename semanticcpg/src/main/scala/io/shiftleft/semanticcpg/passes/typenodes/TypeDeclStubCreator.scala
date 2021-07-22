package io.shiftleft.semanticcpg.passes.typenodes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.{NewTypeDecl, TypeDeclBase}
import io.shiftleft.codepropertygraph.generated.{NodeTypes, nodes}
import io.shiftleft.passes.{SimpleCpgPassV2, DiffGraph}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.structure.{FileTraversal, NamespaceTraversal}

/**
  * This pass has no other pass as prerequisite.
  * For each `TYPE` node that does not have a corresponding `TYPE_DECL`
  * node, this pass creates a `TYPE_DECL` node. The `TYPE_DECL` is
  * considered external.
  */
class TypeDeclStubCreator(cpg: Cpg) extends SimpleCpgPassV2 {

  private var typeDeclFullNameToNode = Map[String, TypeDeclBase]()

  override def run(): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder

    init()

    cpg.typ
      .filterNot(typ => typeDeclFullNameToNode.isDefinedAt(typ.fullName))
      .foreach { typ =>
        val newTypeDecl = createTypeDeclStub(typ.name, typ.fullName)
        typeDeclFullNameToNode += typ.fullName -> newTypeDecl
        dstGraph.addNode(newTypeDecl)
      }

    Iterator(dstGraph.build())
  }

  private def createTypeDeclStub(name: String, fullName: String): NewTypeDecl = {
    nodes
      .NewTypeDecl()
      .name(name)
      .fullName(fullName)
      .isExternal(true)
      .inheritsFromTypeFullName(Nil)
      .astParentType(NodeTypes.NAMESPACE_BLOCK)
      .astParentFullName(NamespaceTraversal.globalNamespaceName)
      .filename(FileTraversal.UNKNOWN)
  }

  override def init(): Unit = {
    cpg.typeDecl
      .sideEffect { typeDecl =>
        typeDeclFullNameToNode += typeDecl.fullName -> typeDecl
      }
      .exec()
  }
}
