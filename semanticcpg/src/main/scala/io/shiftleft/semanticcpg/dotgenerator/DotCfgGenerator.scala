package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import scala.jdk.CollectionConverters._

object DotCfgGenerator {

  def toDotCfg[T <: nodes.CfgNode](step: NodeSteps[T]): Steps[String] = step.map(dotCfg)

  def dotCfg(cfgRoot: nodes.CfgNode): String = {
    cfgRoot match {
      case method: nodes.Method =>
        val sb = Shared.namedGraphBegin(method)
        sb.append(nodesAndEdges(method).mkString("\n"))
        Shared.graphEnd(sb)
      case _ =>
        System.err.println("dotCfg only makes sense for methods")
        ""
    }
  }

  private def nodesAndEdges(methodNode: nodes.Method): List[String] = {

    def shouldBeDisplayed(v: nodes.Node): Boolean = !(
      v.isInstanceOf[nodes.Literal] ||
        v.isInstanceOf[nodes.Identifier] ||
        v.isInstanceOf[nodes.Block]
    )

    val vertices = methodNode.start.cfgNode.l ++ List(methodNode, methodNode.methodReturn)
    val verticesToDisplay = vertices.filter(shouldBeDisplayed)

    def visibleNeighbors(v: nodes.CfgNode, visited: List[nodes.StoredNode] = List()): List[nodes.StoredNode] = {
      if (visited.contains(v)) {
        List()
      } else {
        val children = v._cfgOut().asScala.filter(vertices.contains)
        val (visible, invisible) = children.partition(shouldBeDisplayed)
        visible.toList ++ invisible.toList.flatMap { n =>
          visibleNeighbors(n.asInstanceOf[nodes.CfgNode], visited ++ List(v))
        }
      }
    }

    val edges = verticesToDisplay.map { v =>
      (v.getId, visibleNeighbors(v).map(_.getId))
    }

    val nodeStrings = verticesToDisplay.map { node =>
      s""""${node.getId}" [label = "${Shared.stringRepr(node)}" ]""".stripMargin
    }

    val edgeStrings = edges.flatMap {
      case (id, childIds) =>
        childIds.map(childId => s"""  "$id" -> "$childId"  """)
    }

    nodeStrings ++ edgeStrings
  }

}
