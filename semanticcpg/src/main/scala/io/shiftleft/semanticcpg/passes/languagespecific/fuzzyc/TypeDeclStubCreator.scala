package io.shiftleft.semanticcpg.passes.languagespecific.fuzzyc

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{NodeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph, ParallelIteratorExecutor}
import io.shiftleft.semanticcpg.language._

import scala.collection.parallel.CollectionConverters._

/**
  * This pass has no other pass as prerequisite.
  * For each `TYPE` node that does not have a corresponding `TYPE_DECL`
  * node, this pass creates a `TYPE_DECL` node. The `TYPE_DECL` is
  * considered external.
  */
class TypeDeclStubCreator(cpg: Cpg) extends CpgPass(cpg) {

  private var typeDeclFullNameToNode =
    scala.collection.concurrent.TrieMap[String, nodes.TypeDeclBase]()

  override def run(): Iterator[DiffGraph] = {

    init()

    val CHUNK_SIZE = 128
    new ParallelIteratorExecutor[List[Vertex]](
      cpg.graph.V
        .hasLabel(NodeTypes.TYPE)
        .l
        .grouped(CHUNK_SIZE)).map { group =>
      implicit val dstGraph: DiffGraph.Builder = DiffGraph.newBuilder
      group.foreach { vertex =>
        val typ = vertex.asInstanceOf[nodes.Type]
        typeDeclFullNameToNode.get(typ.fullName) match {
          case Some(_) =>
          case None =>
            val newTypeDecl = createTypeDeclStub(typ.name, typ.fullName)
            typeDeclFullNameToNode += typ.fullName -> newTypeDecl
            dstGraph.addNode(newTypeDecl)
        }
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
    cpg.typeDecl.l.par.foreach { typeDecl =>
      typeDeclFullNameToNode += typeDecl.fullName -> typeDecl
    }
  }
}
