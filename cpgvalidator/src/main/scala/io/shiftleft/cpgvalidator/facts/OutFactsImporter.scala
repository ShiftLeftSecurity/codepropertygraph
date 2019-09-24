package io.shiftleft.cpgvalidator.facts

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.cpgvalidator.SuperTypes
import FactConstructionClasses._

class OutFactsImporter extends FactsImporter {

  override def loadFacts: List[OutFact] =
    List(
      NodeTypes.FILE has 0 to N outgoing EdgeTypes.AST to NodeTypes.NAMESPACE_BLOCK,
      NodeTypes.METHOD has 1 outgoing EdgeTypes.AST to NodeTypes.METHOD_RETURN,
      NodeTypes.METHOD has 0 to N outgoing EdgeTypes.AST to NodeTypes.METHOD_PARAMETER_IN,
      NodeTypes.METHOD has 0 to N outgoing EdgeTypes.AST to NodeTypes.MODIFIER,
      NodeTypes.METHOD has 1 outgoing EdgeTypes.AST to NodeTypes.BLOCK,
      NodeTypes.METHOD has 0 to N outgoing EdgeTypes.AST to NodeTypes.TYPE_PARAMETER,
      NodeTypes.METHOD has 0 to N outgoing EdgeTypes.AST to NodeTypes.ANNOTATION,
      NodeTypes.METHOD has 0 to N outgoing EdgeTypes.CFG to SuperTypes.Expression,
      NodeTypes.METHOD_PARAMETER_IN has 0 to N outgoing EdgeTypes.AST to NodeTypes.ANNOTATION,
      NodeTypes.TYPE has 0 to N outgoing EdgeTypes.AST to NodeTypes.TYPE_ARGUMENT,
      NodeTypes.TYPE_DECL has 0 to N outgoing EdgeTypes.AST to NodeTypes.TYPE_PARAMETER,
      NodeTypes.TYPE_DECL has 0 to N outgoing EdgeTypes.AST to NodeTypes.MEMBER,
      NodeTypes.TYPE_DECL has 0 to N outgoing EdgeTypes.AST to NodeTypes.MODIFIER,
      NodeTypes.TYPE_DECL has 0 to N outgoing EdgeTypes.AST to NodeTypes.ANNOTATION,
      NodeTypes.TYPE_DECL has 0 to N outgoing EdgeTypes.BINDS to NodeTypes.BINDING,
      NodeTypes.MEMBER has 0 to N outgoing EdgeTypes.AST to NodeTypes.ANNOTATION,
      NodeTypes.MEMBER has 0 to N outgoing EdgeTypes.AST to NodeTypes.MODIFIER,
      NodeTypes.LITERAL has 1 to N outgoing EdgeTypes.CFG to SuperTypes.Expression or NodeTypes.METHOD_RETURN,
      NodeTypes.CALL has 0 to N outgoing EdgeTypes.AST to SuperTypes.Expression,
      NodeTypes.CALL has 1 to N outgoing EdgeTypes.CFG to SuperTypes.Expression or NodeTypes.METHOD_RETURN,
      NodeTypes.CALL has 0 to 1 outgoing EdgeTypes.RECEIVER to
        NodeTypes.CALL or NodeTypes.IDENTIFIER or NodeTypes.LITERAL or NodeTypes.METHOD_REF or NodeTypes.BLOCK,
      NodeTypes.IDENTIFIER has 1 to N outgoing EdgeTypes.CFG to SuperTypes.Expression,
      NodeTypes.IDENTIFIER has 0 to 1 outgoing EdgeTypes.REF to NodeTypes.LOCAL or NodeTypes.METHOD_PARAMETER_IN,
      NodeTypes.RETURN has 0 to 1 outgoing EdgeTypes.AST to SuperTypes.Expression,
      NodeTypes.RETURN has 1 outgoing EdgeTypes.CFG to NodeTypes.METHOD_RETURN,
      NodeTypes.BLOCK has 0 to N outgoing EdgeTypes.AST to SuperTypes.Expression,
      NodeTypes.BLOCK has 0 to N outgoing EdgeTypes.AST to NodeTypes.LOCAL,
      NodeTypes.BLOCK has 0 to N outgoing EdgeTypes.CFG to SuperTypes.Expression or NodeTypes.METHOD_RETURN,
      NodeTypes.METHOD_REF has 1 to N outgoing EdgeTypes.CFG to SuperTypes.Expression,
      NodeTypes.METHOD_REF has 0 to N outgoing EdgeTypes.CAPTURE to NodeTypes.CLOSURE_BINDING,
      NodeTypes.CLOSURE_BINDING has 1 outgoing EdgeTypes.REF to NodeTypes.LOCAL or NodeTypes.METHOD_PARAMETER_IN,
      NodeTypes.BINDING has 1 outgoing EdgeTypes.REF to NodeTypes.METHOD,
    )

}
