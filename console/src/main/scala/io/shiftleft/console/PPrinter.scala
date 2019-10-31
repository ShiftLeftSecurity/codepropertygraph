package io.shiftleft.console

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.utils.HighlightedSource
import pprint.{PPrinter, Renderer, Result, Tree, Truncated}

object pprinter {

  def create(original: PPrinter): PPrinter =
    new PPrinter(defaultHeight = 99999, additionalHandlers = myAdditionalHandlers(original)) {
      override def tokenize(x: Any,
                            width: Int = defaultWidth,
                            height: Int = defaultHeight,
                            indent: Int = defaultIndent,
                            initialOffset: Int = 0): Iterator[fansi.Str] = {
        val tree = this.treeify(x)
        val renderer = new Renderer(width, colorApplyPrefix, colorLiteral, indent) {
          override def rec(x: Tree, leftOffset: Int, indentCount: Int): Result = x match {
            case Tree.Literal(body) =>
              println("zzz1")
              val fixedString = HighlightedSource(body).fixedForFansi
              Result.fromString(fixedString)
            case _ => super.rec(x, leftOffset, indentCount)
          }
        }
        val rendered = renderer.rec(tree, initialOffset, 0).iter
        new Truncated(rendered, width, height)
      }
    }

  private def myAdditionalHandlers(original: PPrinter): PartialFunction[Any, Tree] = {
    case node: nodes.Node =>
      Tree.Apply(
        node.productPrefix,
        Iterator.range(0, node.productArity).map { n =>
          Tree.Infix(Tree.Literal(node.productElementLabel(n)), "->", original.treeify(node.productElement(n)))
        }
      )
  }
}
