package io.shiftleft.semanticcpg.passes
import scala.collection.mutable
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{nodes, NodeKeyNames}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import scala.jdk.CollectionConverters._

class BindingMethodOverridesPass(cpg: Cpg) extends CpgPass(cpg) {
  val overwritten = mutable.HashSet[nodes.Binding]()
  val bindingTable = scala.collection.mutable.HashMap[(String, String, nodes.TypeDecl), nodes.Binding]()

  override def run(): Iterator[DiffGraph] = {
    val diffGraph = DiffGraph.newBuilder
    for (typeDecl <- cpg.typeDecl.toIterator;
         binding <- typeDecl._bindingViaBindsOut) {
      bindingTable.update((binding.name, binding.signature, typeDecl), binding)
    }
    for (typeDecl <- cpg.typeDecl.toIterator) {
      val parentTypeDecls = typeDecl._typeViaInheritsFromOut.flatMap { _._typeDeclViaRefOut }.toList
      for (binding <- typeDecl._bindingViaBindsOut) {
        if (!overwritten.contains(binding)) {
          val method = binding._methodViaRefOut.get
          for (parentTypeDecl <- parentTypeDecls) {
            val parentBinding = bindingTable.get((binding.name, binding.signature, parentTypeDecl))
            if (parentBinding.isDefined && parentBinding.get._methodViaRefOut.get != method) {
              markRecurse(parentBinding.get)
            }
          }
        }
      }
    }
    for (typeDecl <- cpg.typeDecl.toIterator;
         binding <- typeDecl._bindingViaBindsOut) {
      diffGraph.addNodeProperty(node = binding,
                                key = NodeKeyNames.IS_METHOD_NEVER_OVERRIDDEN,
                                value = (!overwritten.contains(binding)).asInstanceOf[AnyRef])
    }
    return Iterator(diffGraph.build())
  }

  def markRecurse(binding: nodes.Binding): Unit = {
    val wasAlreadyOverwritten = overwritten.add(binding)
    if (wasAlreadyOverwritten) {
      for (parentType <- binding._typeDeclViaBindsIn.next._typeViaInheritsFromOut;
           parentTypeDecl <- parentType._typeDeclViaRefOut;
           parentBinding <- bindingTable.get((binding.name, binding.signature, parentTypeDecl))) {
        markRecurse(parentBinding)
      }
    }
  }
}
