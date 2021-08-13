package io.shiftleft.c2cpg.astcreation

import io.shiftleft.codepropertygraph.generated.nodes._
import org.eclipse.cdt.core.dom.ast.{IASTLabelStatement, IASTNode}

trait AstNodeBuilder {

  this: AstCreator =>

  protected def newUnknown(node: IASTNode, order: Int): NewUnknown =
    NewUnknown()
      .parserTypeName(node.getClass.getSimpleName)
      .code(node.getRawSignature)
      .order(order)
      .argumentIndex(order)
      .lineNumber(line(node))
      .columnNumber(column(node))

  protected def createMethodRefNode(code: String, methodFullName: String, node: IASTNode): NewMethodRef =
    NewMethodRef()
      .code(code)
      .methodFullName(methodFullName)
      .typeFullName(methodFullName)
      .lineNumber(line(node))
      .columnNumber(column(node))

  protected def newCallNode(astNode: IASTNode,
                            name: String,
                            fullname: String,
                            dispatchType: String,
                            order: Int): NewCall =
    NewCall()
      .name(name)
      .dispatchType(dispatchType)
      .signature("TODO")
      .methodFullName(fullname)
      .code(astNode.getRawSignature)
      .order(order)
      .argumentIndex(order)
      .lineNumber(line(astNode))
      .columnNumber(column(astNode))

  protected def newControlStructureNode(node: IASTNode,
                                        controlStructureType: String,
                                        code: String,
                                        order: Int): NewControlStructure =
    NewControlStructure()
      .parserTypeName(node.getClass.getSimpleName)
      .controlStructureType(controlStructureType)
      .code(code)
      .order(order)
      .argumentIndex(order)
      .lineNumber(line(node))
      .columnNumber(column(node))

  protected def newJumpTarget(node: IASTNode, order: Int): NewJumpTarget = {
    val code = node.getRawSignature
    val name = node match {
      case label: IASTLabelStatement    => label.getName.toString
      case _ if code.startsWith("case") => "case"
      case _                            => "default"
    }
    NewJumpTarget()
      .parserTypeName(node.getClass.getSimpleName)
      .name(name)
      .code(code)
      .order(order)
      .argumentIndex(order)
      .lineNumber(line(node))
      .columnNumber(column(node))
  }

}
