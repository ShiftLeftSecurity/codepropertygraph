package io.shiftleft.passes.languagespecific.fuzzyc
import gremlin.scala.ScalaGraph
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{NodeTypes, nodes}
import io.shiftleft.diffgraph.DiffGraph
import io.shiftleft.passes.CpgPass

class TypeDeclStubCreator(graph: ScalaGraph) extends CpgPass(graph) {

  private var typeDeclFullNameToNode = Map[String, nodes.TypeDeclBase]()

  override def run(): Iterator[DiffGraph] = {
    val dstGraph = new DiffGraph

    init()

    graph.V
      .hasLabel(NodeTypes.TYPE)
      .sideEffectWithTraverser { traverser =>
        val typ = traverser.get.asInstanceOf[nodes.Type]
        typeDeclFullNameToNode.get(typ.fullName) match {
          case Some(_) =>
          case None =>
            val newTypeDecl = createTypeDeclStub(typ.name, typ.fullName)
            typeDeclFullNameToNode += typ.fullName -> newTypeDecl
            dstGraph.addNode(newTypeDecl)
        }
      }
      .iterate()

    Iterator(dstGraph)
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
    val cpg = Cpg(graph.graph)
    cpg.typeDecl.sideEffect { typeDecl =>
      typeDeclFullNameToNode += typeDecl.fullName -> typeDecl
    }
  }
}
