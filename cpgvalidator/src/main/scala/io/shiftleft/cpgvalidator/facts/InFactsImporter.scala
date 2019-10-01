package io.shiftleft.cpgvalidator.facts

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.cpgvalidator.SuperTypes
import io.shiftleft.cpgvalidator.facts.FactConstructionClasses._

class InFactsImporter extends FactsImporter {

  override def loadFacts: List[InFact] =
    List(
      NodeTypes.METHOD has 0 to N incoming EdgeTypes.VTABLE from NodeTypes.TYPE_DECL,
      NodeTypes.METHOD_PARAMETER_IN has 1 incoming EdgeTypes.AST from NodeTypes.METHOD,
      NodeTypes.METHOD_PARAMETER_IN has 0 to N incoming EdgeTypes.REF from NodeTypes.IDENTIFIER or NodeTypes.CLOSURE_BINDING,
      NodeTypes.METHOD_RETURN has 1 incoming EdgeTypes.AST from NodeTypes.METHOD,
      NodeTypes.METHOD_RETURN has 0 to N incoming EdgeTypes.CFG from NodeTypes.RETURN,
      NodeTypes.MODIFIER has 1 incoming EdgeTypes.AST from
        NodeTypes.METHOD or NodeTypes.METHOD_PARAMETER_IN or NodeTypes.TYPE_DECL or NodeTypes.MEMBER,
      NodeTypes.TYPE has 0 to N incoming EdgeTypes.REF from NodeTypes.TYPE_ARGUMENT,
      NodeTypes.TYPE_PARAMETER has 1 incoming EdgeTypes.AST from NodeTypes.METHOD or NodeTypes.TYPE_DECL,
      NodeTypes.TYPE_PARAMETER has 0 to N incoming EdgeTypes.BINDS_TO from NodeTypes.TYPE_ARGUMENT,
      NodeTypes.MEMBER has 1 incoming EdgeTypes.AST from NodeTypes.TYPE_DECL,
      NodeTypes.NAMESPACE_BLOCK has 0 to 1 incoming EdgeTypes.AST from NodeTypes.FILE,
      NodeTypes.LITERAL has 1 incoming EdgeTypes.AST from SuperTypes.Expression,
      NodeTypes.LITERAL has 0 to N incoming EdgeTypes.CFG from SuperTypes.Expression or NodeTypes.METHOD,
      NodeTypes.LITERAL has 0 to 1 incoming EdgeTypes.RECEIVER from NodeTypes.CALL,
      NodeTypes.CALL has 1 incoming EdgeTypes.AST from SuperTypes.Expression,
      NodeTypes.CALL has 0 to N incoming EdgeTypes.CFG from SuperTypes.Expression or NodeTypes.METHOD,
      NodeTypes.CALL has 0 to 1 incoming EdgeTypes.RECEIVER from NodeTypes.CALL,
      NodeTypes.LOCAL has 1 incoming EdgeTypes.AST from NodeTypes.BLOCK,
      NodeTypes.LOCAL has 0 to N incoming EdgeTypes.REF from NodeTypes.IDENTIFIER,
      NodeTypes.LOCAL has 0 to N incoming EdgeTypes.REF from NodeTypes.CLOSURE_BINDING,
      NodeTypes.IDENTIFIER has 1 incoming EdgeTypes.AST from SuperTypes.Expression,
      NodeTypes.IDENTIFIER has 0 to N incoming EdgeTypes.CFG from SuperTypes.Expression or NodeTypes.METHOD,
      NodeTypes.IDENTIFIER has 0 to 1 incoming EdgeTypes.RECEIVER from NodeTypes.CALL,
      NodeTypes.RETURN has 1 incoming EdgeTypes.AST from SuperTypes.Expression,
      NodeTypes.RETURN has 0 to N incoming EdgeTypes.CFG from SuperTypes.Expression or NodeTypes.METHOD,
      NodeTypes.BLOCK has 1 incoming EdgeTypes.AST from SuperTypes.Expression or NodeTypes.METHOD,
      NodeTypes.BLOCK has 0 to N incoming EdgeTypes.CFG from SuperTypes.Expression or NodeTypes.METHOD,
      NodeTypes.BLOCK has 0 to 1 incoming EdgeTypes.RECEIVER from NodeTypes.CALL,
      NodeTypes.METHOD_REF has 1 incoming EdgeTypes.AST from SuperTypes.Expression,
      NodeTypes.METHOD_REF has 0 to N incoming EdgeTypes.CFG from SuperTypes.Expression or NodeTypes.METHOD,
      NodeTypes.METHOD_REF has 0 to 1 incoming EdgeTypes.RECEIVER from NodeTypes.CALL,
    )

}
