package io.shiftleft.c2cpg.astcreation

import io.shiftleft.codepropertygraph.generated.DispatchTypes
import io.shiftleft.codepropertygraph.generated.nodes.{AstNodeNew, NewCall, NewLocal}
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
    * as its child. Also create a METHOD node to represent the macro if it has
    * not been created as part of processing the current translation unit.
    * Removal of duplicates that result from usage of the same macro in multiple
    * compilation units is deferred to a pass that runs once all ASTs have been
    * created.
    * */
  def asChildOfMacroCall(node: IASTNode, ast: Ast, order: Int): Ast = {
    val macroCallAst = extractMatchingMacro(node).map {
      case (mac, args) =>
        createMacroCallAst(ast, node, mac, args, order)
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
      .filterNot { m =>
        isExpandedFrom(node.getParent, m)
      }
      .foreach { m =>
        val nodeOffset = node.getFileLocation.getNodeOffset
        val macroName = m.getExpansion.getMacroDefinition.getName.toString
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
    * Determine whether `node` is expanded from the macro expansion
    * at `loc`.
    * */
  private def isExpandedFrom(node: IASTNode, loc: IASTMacroExpansionLocation) = {
    expandedFromMacro(node)
      .map(_.getExpansion.getMacroDefinition)
      .contains(loc.getExpansion.getMacroDefinition)
  }

  private def argumentTrees(arguments: List[String], ast: Ast): List[Option[Ast]] = {
    arguments.map { arg =>
      val rootNode =
        ast.nodes.find(x => !x.isInstanceOf[NewLocal] && x.properties.get("CODE").contains(arg.replace(" ", "")))
      rootNode.map { x =>
        ast.subTreeCopy(x.asInstanceOf[AstNodeNew], 1)
      }
    }
  }

  /**
    * Create an AST that represents a macro expansion as a call.
    * The AST is rooted in a CALL node and contains sub trees
    * for arguments. These are also connected to the AST via
    * ARGUMENT edges.
    * */
  private def createMacroCallAst(ast: Ast,
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

    val argAsts = argumentTrees(arguments, ast).map(_.getOrElse(Ast()))
    Ast(callNode)
      .withChildren(argAsts)
      .withArgEdges(callNode, argAsts.flatMap(x => x.root))
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
