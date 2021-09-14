package io.shiftleft.c2cpg.astcreation

import io.shiftleft.codepropertygraph.generated.DispatchTypes
import io.shiftleft.codepropertygraph.generated.nodes.NewCall
import io.shiftleft.x2cpg.Ast
import org.eclipse.cdt.core.dom.ast.{
  IASTMacroExpansionLocation,
  IASTNode,
  IASTPreprocessorFunctionStyleMacroDefinition,
  IASTPreprocessorMacroDefinition
}
import org.eclipse.cdt.internal.core.parser.scanner.C2CpgMacroExplorer

import scala.annotation.nowarn
import scala.collection.mutable

trait MacroHandler {

  this: AstCreator =>

  /**
    * For the given node, determine if it is expanded from a macro, and if so,
    * create a Call node to represent the macro invocation and attach `ast`
    * as its child.
    * */
  def asChildOfMacroCall(node: IASTNode, ast: Ast): Ast = {
    val macroCallAst = extractMatchingMacro(node).map { case (mac, args) => createMacroCall(node, mac, args) }
    if (macroCallAst.isDefined) {
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
  private def extractMatchingMacro(
      node: IASTNode): Option[(IASTPreprocessorFunctionStyleMacroDefinition, List[String])] = {
    expandedFromMacro(node).foreach { expandedFrom =>
      val nodeOffset = node.getFileLocation.getNodeOffset
      val macroName = expandedFrom.getExpansion.getMacroDefinition.getName.toString
      while (nodeOffsetMacroPairs.headOption.exists(
               x => x._1 <= nodeOffset && x._2.isInstanceOf[IASTPreprocessorFunctionStyleMacroDefinition])) {
        val (_, macroDefinition: IASTPreprocessorFunctionStyleMacroDefinition) = nodeOffsetMacroPairs.head
        nodeOffsetMacroPairs.remove(0)
        val name = macroDefinition.getName.toString
        if (macroName == name) {
          val arguments = new C2CpgMacroExplorer(parserResult, node.getFileLocation).getArguments
          return Some((macroDefinition, arguments))
        }
      }
    }
    None
  }

  /**
    * Create a CALL node that represents the macro invocation where
    * `node` is the node is the root node of the expansion and
    * `macroDef` is the macro definition.
    * */
  private def createMacroCall(node: IASTNode,
                              macroDef: IASTPreprocessorFunctionStyleMacroDefinition,
                              args: List[String]): Ast = {
    val name = macroDef.getName.toString
    val code = node.getRawSignature.replaceAll(";$", "")
    println(args)
    val callNode = NewCall()
      .name(name)
      .methodFullName(name)
      .code(code)
      .lineNumber(line(node))
      .columnNumber(column(node))
      .typeFullName(typeFor(node))
      .dispatchType(DispatchTypes.STATIC_DISPATCH)
    Ast(callNode)
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
      node.getNodeLocations.headOption.collect { case x: IASTMacroExpansionLocation => x }
    } else {
      None
    }
  }

}
