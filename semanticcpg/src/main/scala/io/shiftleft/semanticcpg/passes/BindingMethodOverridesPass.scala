package io.shiftleft.semanticcpg.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{nodes, NodeKeyNames}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import scala.jdk.CollectionConverters._

class BindingMethodOverridesPass(cpg: Cpg) extends CpgPass(cpg) {
  val overwritten = scala.collection.mutable.HashSet[nodes.Binding]()
  val bindingTable = scala.collection.mutable.HashMap[(String, String, nodes.TypeDecl), nodes.Binding]()

  override def run(): Iterator[DiffGraph] = {
    val diffGraph = DiffGraph.newBuilder
    for (typeDecl <- cpg.typeDecl.toIterator;
         binding <- typeDecl.bindsOut.asScala) {
      bindingTable.update((binding.name, binding.signature, typeDecl), binding)
    }
    for (typeDecl <- cpg.typeDecl.toIterator) {
      val parentTypeDecls = typeDecl.inheritsFromOut.asScala.map {
        _.refOut.next
      }.toList
      for (binding <- typeDecl.bindsOut.asScala) {
        if (!overwritten.contains(binding)) {
          val method = binding.refOut.next
          for (parentTypeDecl <- parentTypeDecls) {
            val parentBinding = bindingTable.get((binding.name, binding.signature, parentTypeDecl))
            if (parentBinding.isDefined && parentBinding.get.refOut.next != method) {
              markRecurse(parentBinding.get)
            }
          }
        }
      }
    }
    for (typeDecl <- cpg.typeDecl.toIterator;
         binding <- typeDecl.bindsOut.asScala) {
      diffGraph.addNodeProperty(node = binding,
                                key = NodeKeyNames.IS_METHOD_NEVER_OVERRIDDEN,
                                value = (!overwritten.contains(binding)).asInstanceOf[AnyRef])
    }
    return Some(diffGraph.build()).iterator
  }

  def markRecurse(binding: nodes.Binding): Unit = {
    if (overwritten.add(binding)) {
      for (parentType <- binding.bindsIn.next.inheritsFromOut.asScala;
           parentTypeDecl <- parentType.refOut.asScala;
           parentBinding <- bindingTable.get((binding.name, binding.signature, parentTypeDecl))) {
        markRecurse(parentBinding)
      }
    }
  }
}
