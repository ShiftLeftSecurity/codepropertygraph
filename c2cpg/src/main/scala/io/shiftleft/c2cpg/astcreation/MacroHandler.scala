package io.shiftleft.c2cpg.astcreation

import io.shiftleft.codepropertygraph.generated.DispatchTypes
import io.shiftleft.codepropertygraph.generated.nodes.{NewCall, NewIdentifier}
import io.shiftleft.x2cpg.Ast
import org.eclipse.cdt.core.dom.ast.{IASTMacroExpansionLocation, IASTNode, IASTPreprocessorMacroDefinition}
import org.eclipse.cdt.internal.core.parser.scanner.MacroArgumentExtractor

import scala.annotation.nowarn
import scala.collection.mutable

trait MacroHandler {

  this: AstCreator =>

  /**
    * For the given node, determine if it is expanded from a macro, and if so,
    * create a Call node to represent the macro invocation and attach `ast`
    * as its child.
    * */
  def asChildOfMacroCall(node: IASTNode, ast: Ast, order: Int): Ast = {
    val macroCallAst = extractMatchingMacro(node).map {
      case (mac, args) =>
        createMacroCallAst( // ast,
                           node,
                           mac,
                           args,
                           order)
    }
    if (macroCallAst.isDefined) {
      // TODO order/argument_index of `ast`'s root needs to be set to 1
      macroCallAst.get.withChild(ast)
    } else {
      ast
    }
  }

  /**
    * For the given node, determine if it is expanded from a macro, and if so, find the first
    * matching (offset, macro) pair in nodeOffsetMacroPairs, removing non-matching elements
    * from the start of nodeOffsetMacroPairs. Returns (Some(macroDefinition, arguments))
    * if a macro definition matches and None otherwise.
    * */
  private def extractMatchingMacro(node: IASTNode): Option[(IASTPreprocessorMacroDefinition, List[String])] = {
    expandedFromMacro(node)
      .filterNot { expandedFrom =>
        expandedFromMacro(node.getParent)
          .map(_.getExpansion.getMacroDefinition)
          .contains(expandedFrom.getExpansion.getMacroDefinition)
      }
      .foreach { expandedFrom =>
        val nodeOffset = node.getFileLocation.getNodeOffset
        val macroName = expandedFrom.getExpansion.getMacroDefinition.getName.toString
        while (nodeOffsetMacroPairs.headOption.exists(x => x._1 <= nodeOffset)) {
          val (_, macroDefinition) = nodeOffsetMacroPairs.head
          nodeOffsetMacroPairs.remove(0)
          val name = macroDefinition.getName.toString
          if (macroName == name) {
            val arguments = new MacroArgumentExtractor(parserResult, node.getFileLocation).getArguments
            return Some((macroDefinition, arguments))
          }
        }
      }
    None
  }

  /**
    * Create an AST that represents a macro expansion as a call.
    * The AST is rooted in a CALL node and contains sub trees
    * for arguments. These are also connected to the AST via
    * ARGUMENT edges.
    * */
  private def createMacroCallAst( // ast: Ast,
                                 node: IASTNode,
                                 macroDef: IASTPreprocessorMacroDefinition,
                                 arguments: List[String],
                                 order: Int): Ast = {
    val name = macroDef.getName.toString
    val code = node.getRawSignature.replaceAll(";$", "")
    val callNode = NewCall()
      .name(name)
      .methodFullName(name)
      .code(code)
      .lineNumber(line(node))
      .columnNumber(column(node))
      .typeFullName(typeFor(node))
      .dispatchType(DispatchTypes.INLINED)
      .order(order)
      .argumentIndex(order)

    // TODO We want to clone the ASTS of arguments here
    // and then attach those ASTS to the AST we return
    // For now, we instead create identifier nodes
    // val nodes = ast.nodes
    // val argRoots = arguments.flatMap { arg =>
    //  nodes.find(x => x.properties.get("CODE").contains(arg))
    // }

    val argAsts = arguments.zipWithIndex.map {
      case (arg, i) =>
        Ast(NewIdentifier(name = arg, code = arg, order = i + 1, argumentIndex = i + 1))
    }

    Ast(callNode)
      .withChildren(argAsts)
      .withArgEdges(callNode, argAsts.flatMap(_.root))
  }

  private val nodeOffsetMacroPairs: mutable.Buffer[(Int, IASTPreprocessorMacroDefinition)] = {
    parserResult.getNodeLocations.toList
      .collect {
        case exp: IASTMacroExpansionLocation =>
          (exp.asFileLocation().getNodeOffset, exp.getExpansion.getMacroDefinition)
      }
      .sortBy(_._1)
      .toBuffer
  }

  /** The CDT utility method is unfortunately in a class that is marked as deprecated, however,
    * this is because the CDT team would like to discourage its use but at the same time does
    * not plan to remove this code.
    */
  @nowarn
  def nodeSignature(node: IASTNode): String = {
    import org.eclipse.cdt.core.dom.ast.ASTSignatureUtil.getNodeSignature
    if (isExpandedFromMacro(node)) {
      getNodeSignature(node)
    } else {
      node.getRawSignature
    }
  }

  def isExpandedFromMacro(node: IASTNode): Boolean =
    expandedFromMacro(node).nonEmpty

  def expandedFromMacro(node: IASTNode): Option[IASTMacroExpansionLocation] = {
    val locations = node.getNodeLocations
    if (locations.nonEmpty) {
      node.getNodeLocations.headOption
        .collect { case x: IASTMacroExpansionLocation => x }
    } else {
      None
    }
  }

}
