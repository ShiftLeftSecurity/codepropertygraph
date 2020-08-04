package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{nodes, _}
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess

import scala.collection.mutable
import scala.jdk.CollectionConverters._

class ReachingDefPass(cpg: Cpg) extends ParallelCpgPass[nodes.Method](cpg) {

  import DataFlowFrameworkHelper._

  override def partIterator: Iterator[nodes.Method] = cpg.method.toIterator()

  override def runOnPart(method: nodes.Method): Iterator[DiffGraph] = {

    val entryNode = method
    val exitNode = method.methodReturn

    val allCfgNodes = method.cfgNode.toList ++ List(entryNode, exitNode)
    val nodeToGens = methodToGenMap(method).withDefaultValue(Set.empty[nodes.StoredNode])
    val nodeToKills = methodToKillMap(method).withDefaultValue(Set.empty[nodes.StoredNode])
    var out: Map[nodes.CfgNode, Set[nodes.StoredNode]] =
      allCfgNodes
        .filter(nodeToGens.contains)
        .map { cfgNode =>
          cfgNode -> nodeToGens(cfgNode)
        }
        .toMap
        .withDefaultValue(Set.empty[nodes.StoredNode])

    var in = Map
      .empty[nodes.CfgNode, Set[nodes.StoredNode]]
      .withDefaultValue(Set.empty[nodes.StoredNode])

    val worklist = mutable.Set.empty[nodes.CfgNode]
    worklist ++= allCfgNodes
    while (worklist.nonEmpty) {
      val currentCfgNode = worklist.head
      worklist -= currentCfgNode

      val inSet = currentCfgNode.start.cfgPrev
        .map { cfgPredecessor =>
          out(cfgPredecessor)
        }
        .reduceOption((x, y) => x.union(y))
        .getOrElse(Set.empty[nodes.StoredNode])

      in += currentCfgNode -> inSet

      val oldSize = out(currentCfgNode).size
      val gens = nodeToGens(currentCfgNode)
      val kills = nodeToKills(currentCfgNode)

      out += currentCfgNode -> gens.union(inSet.diff(kills))
      val newSize = out(currentCfgNode).size

      if (oldSize != newSize)
        worklist ++= currentCfgNode.start.cfgNext.l
    }

    val dstGraph = DiffGraph.newBuilder
    addReachingDefEdges(dstGraph, method, out, in)
    Iterator(dstGraph.build())
  }

  /** Pruned DDG, i.e., two call assignment nodes are adjacent if a
    * reaching definition edge reaches a node where the definition is used.
    * The final representation makes it straightforward to build def-use/use-def chains */
  private def addReachingDefEdges(dstGraph: DiffGraph.Builder,
                                  method: nodes.Method,
                                  outSet: Map[nodes.CfgNode, Set[nodes.StoredNode]],
                                  inSet: Map[nodes.CfgNode, Set[nodes.StoredNode]]): Unit = {

    def addEdge(fromNode: nodes.StoredNode, toNode: nodes.StoredNode, variable: String = ""): Unit = {
      val properties = List((EdgeKeyNames.VARIABLE, variable))
      dstGraph.addEdgeInOriginal(fromNode, toNode, EdgeTypes.REACHING_DEF, properties)
    }

    for {
      methodParameterIn <- method._methodParameterInViaAstOut
      refInIdentifier <- methodParameterIn._refIn.asScala
      operationNode <- getOperation(refInIdentifier)
    } addEdge(methodParameterIn, operationNode, methodParameterIn.name)

    val methodReturn = method.methodReturn
    methodReturn.toReturn.foreach(returnNode => addEdge(returnNode, methodReturn))

    outSet.foreach {
      case (call: nodes.Call, outDefs) =>
        handleCall(call, outDefs)
      case (ret: nodes.Return, _) =>
        ret._astOut.asScala.foreach { returnExpr =>
          val localRef = reference(returnExpr)
          inSet(ret)
            .filter(inElement => localRef == reference(inElement))
            .toList
            .flatMap { filteredInElement =>
              getExpressionFromGen(filteredInElement).map(addEdge(_, ret))
            }
            .headOption
            .getOrElse(addEdge(method, ret))
        }
      case _ => // ignore
    }

    def handleCall(call: Call, outDefs: Set[StoredNode]) = {
      val usesInExpression = getUsesOfExpression(call)
      val localRefsUses = usesInExpression.map(reference).filter(_ != None)

      if (inSet(call).isEmpty) {
        addEdge(method, call)
      }

      /* if use is not an identifier, add edge, as we are going to visit the use separately */
      usesInExpression.foreach { use =>
        if (!use.isInstanceOf[Identifier] && !use.isInstanceOf[Literal] && !use
              .isInstanceOf[FieldIdentifier]) {
          addEdge(use, call)

          /* handle indirect access uses: check if we have it in our out set and get
           * the corresponding def expression from which the definition reaches the use
           */
          if (isIndirectAccess(use)) {
            outDefs.filter(out => isIndirectAccess(out)).foreach { indirectOutDef =>
              val indirectOutCall = indirectOutDef.asInstanceOf[Call]
              if (indirectOutCall.code == use.asInstanceOf[Call].code) {
                val expandedToCall = indirectOutCall.parentExpression
                addEdge(expandedToCall, use)
              }
            }
          }
        }
      }

      if (isOperationAndAssignment(call)) {
        val localRefGens = getGensOfExpression(call).map(reference)
        inSet(call)
          .filter(inElement => localRefGens.contains(reference(inElement)))
          .foreach { filteredInElement =>
            getExpressionFromGen(filteredInElement).foreach(x => addEdge(x, call))
          }
      }

      for (elem <- outDefs) {
        val localRefGen = reference(elem)

        getExpressionFromGen(elem).foreach { expressionOfElement =>
          if (expressionOfElement != call && localRefsUses.contains(localRefGen)) {
            addEdge(expressionOfElement, call, elem.asInstanceOf[nodes.CfgNode].code)
          }
        }
      }
    }
  }

  private def reference(node: nodes.StoredNode): Option[nodes.StoredNode] =
    node._refOut.nextOption

  private def isOperationAndAssignment(call: nodes.Call): Boolean = {
    call.name match {
      case Operators.assignmentAnd                  => true
      case Operators.assignmentArithmeticShiftRight => true
      case Operators.assignmentDivision             => true
      case Operators.assignmentExponentiation       => true
      case Operators.assignmentLogicalShiftRight    => true
      case Operators.assignmentMinus                => true
      case Operators.assignmentModulo               => true
      case Operators.assignmentMultiplication       => true
      case Operators.assignmentOr                   => true
      case Operators.assignmentPlus                 => true
      case Operators.assignmentShiftLeft            => true
      case Operators.assignmentXor                  => true
      case _                                        => false
    }
  }

  private def isIndirectAccess(node: nodes.StoredNode): Boolean =
    node match {
      case call: nodes.Call => MemberAccess.isGenericMemberAccessName(call.name)
      case _                => false
    }

}

/** Common functionalities needed for data flow frameworks */
object DataFlowFrameworkHelper {

  private def callToMethodParamOut(call: nodes.StoredNode): Iterable[nodes.StoredNode] = {
    NoResolve
      .getCalledMethods(call.asInstanceOf[nodes.Call])
      .flatMap(method => method.parameter.asOutput)
  }

  private def filterArgumentIndex(nodeList: List[nodes.StoredNode], orderSeq: Iterable[Int]): List[nodes.StoredNode] = {
    nodeList.filter(node => orderSeq.exists(_ == node.asInstanceOf[nodes.HasArgumentIndex].argumentIndex.toInt))
  }

  def getExpressions(method: nodes.Method): List[nodes.Call] =
    method._callViaContainsOut.to(List)

  def getGensOfExpression(expr: nodes.StoredNode): Set[nodes.StoredNode] = {
    var gens = Set[nodes.StoredNode]()
    val methodParamOutsOrder = callToMethodParamOut(expr)
      .filter(methPO => methPO._propagateIn.hasNext)
      .map(_.asInstanceOf[nodes.HasOrder].order.toInt)

    val identifierWithOrder =
      filterArgumentIndex(expr._argumentOut.asScala.toList, methodParamOutsOrder)
    gens ++= identifierWithOrder

    gens
  }

  def getUsesOfExpression(expr: nodes.StoredNode): Set[nodes.StoredNode] = {
    expr._argumentOut.asScala
      .filter(!getGensOfExpression(expr).contains(_))
      .toSet
  }

  def getExpressionFromGen(genVertex: nodes.StoredNode): Option[nodes.StoredNode] = {
    getOperation(genVertex).filter(_.isInstanceOf[nodes.Call])
  }

  /** Returns a set of nodes that are killed by the passed nodes */
  def killsVertices(node: nodes.StoredNode): Set[nodes.StoredNode] = {
    val localRefIt = node._refOut.asScala

    if (!localRefIt.hasNext) {
      Set()
    } else {
      val localRef = localRefIt.next
      localRef._refIn.asScala.filter(_.id != node.id2).toSet
    }
  }

  def kills(node: Set[nodes.StoredNode]): Set[nodes.StoredNode] = {
    node.map(v => killsVertices(v)).fold(Set())((v1, v2) => v1.union(v2))
  }

  def methodToKillMap(method: nodes.Method): Map[nodes.StoredNode, Set[nodes.StoredNode]] = {
    val genExpressions = getExpressions(method)

    genExpressions.map { expression =>
      val gens = getGensOfExpression(expression)
      expression -> kills(gens)
    }.toMap
  }

  def methodToGenMap(method: nodes.Method): Map[nodes.StoredNode, Set[nodes.StoredNode]] = {
    /*genExpressions correspond to call assignment nodes*/
    val genExpressions = getExpressions(method)
    val ret: Map[nodes.StoredNode, Set[StoredNode]] = genExpressions.map { genExpression =>
      genExpression -> getGensOfExpression(genExpression)
    }.toMap
    ret + (method -> ret.values.to(List).reduceOption((x, y) => x.union(y)).getOrElse(Set()))
  }

  def getOperation(node: nodes.StoredNode): Option[nodes.StoredNode] = {
    node match {
      case identifier: nodes.Identifier => identifier._argumentIn.nextOption.flatMap(getOperation)
      case _: nodes.Call                => Some(node)
      case _: nodes.Return              => Some(node)
      case _                            => None
    }
  }
}
