package io.shiftleft.cpgvalidator.facts

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.cpgvalidator.SuperTypes
import io.shiftleft.cpgvalidator.facts.FactConstructionClasses._

class InFactsImporter extends FactsImporter {

  override def loadFacts: List[InFact] =
    List(
      NodeTypes.METHOD has 0 to 1 incoming EdgeTypes.AST from NodeTypes.METHOD,
      NodeTypes.METHOD has 0 to 1 incoming EdgeTypes.AST from NodeTypes.TYPE_DECL,
      NodeTypes.METHOD has 0 to N incoming EdgeTypes.REF from NodeTypes.BINDING,
      NodeTypes.METHOD has 0 to 1 incoming EdgeTypes.AST from NodeTypes.NAMESPACE_BLOCK,
      NodeTypes.METHOD_PARAMETER_IN has 1 incoming EdgeTypes.AST from NodeTypes.METHOD,
      NodeTypes.METHOD_PARAMETER_IN has 0 to N incoming EdgeTypes.REF from NodeTypes.IDENTIFIER or NodeTypes.CLOSURE_BINDING,
      NodeTypes.METHOD_RETURN has 1 incoming EdgeTypes.AST from NodeTypes.METHOD,
      NodeTypes.METHOD_RETURN has 0 to N incoming EdgeTypes.CFG from SuperTypes.Expression or NodeTypes.METHOD,
      NodeTypes.MODIFIER has 1 incoming EdgeTypes.AST from
        NodeTypes.METHOD or NodeTypes.METHOD_PARAMETER_IN or NodeTypes.TYPE_DECL or NodeTypes.MEMBER,
      NodeTypes.TYPE has 0 to N incoming EdgeTypes.REF from NodeTypes.TYPE_ARGUMENT,
      NodeTypes.TYPE_PARAMETER has 1 incoming EdgeTypes.AST from NodeTypes.METHOD or NodeTypes.TYPE_DECL,
      NodeTypes.TYPE_PARAMETER has 0 to N incoming EdgeTypes.BINDS_TO from NodeTypes.TYPE_ARGUMENT,
      NodeTypes.MEMBER has 1 incoming EdgeTypes.AST from NodeTypes.TYPE_DECL,
      NodeTypes.NAMESPACE_BLOCK has 0 to 1 incoming EdgeTypes.AST from NodeTypes.FILE,
      NodeTypes.NAMESPACE_BLOCK has 0 to 1 incoming EdgeTypes.AST from NodeTypes.METHOD,
      NodeTypes.NAMESPACE_BLOCK has 0 to 1 incoming EdgeTypes.AST from NodeTypes.TYPE_DECL,
      NodeTypes.LITERAL has 1 incoming EdgeTypes.AST from SuperTypes.Expression or NodeTypes.CONTROL_STRUCTURE,
      NodeTypes.LITERAL has 0 to N incoming EdgeTypes.CFG from SuperTypes.Expression or NodeTypes.METHOD,
      NodeTypes.LITERAL has 0 to 1 incoming EdgeTypes.RECEIVER from NodeTypes.CALL,
      NodeTypes.LITERAL has 0 to 1 incoming EdgeTypes.ARGUMENT from NodeTypes.CALL or NodeTypes.RETURN,
      NodeTypes.CALL has 1 incoming EdgeTypes.AST from SuperTypes.Expression or NodeTypes.CONTROL_STRUCTURE,
      NodeTypes.CALL has 0 to N incoming EdgeTypes.CFG from SuperTypes.Expression or NodeTypes.METHOD,
      NodeTypes.CALL has 0 to 1 incoming EdgeTypes.RECEIVER from NodeTypes.CALL,
      NodeTypes.CALL has 0 to 1 incoming EdgeTypes.ARGUMENT from NodeTypes.CALL or NodeTypes.RETURN,
      NodeTypes.LOCAL has 1 incoming EdgeTypes.AST from NodeTypes.BLOCK,
      NodeTypes.LOCAL has 0 to N incoming EdgeTypes.REF from NodeTypes.IDENTIFIER,
      NodeTypes.LOCAL has 0 to N incoming EdgeTypes.REF from NodeTypes.CLOSURE_BINDING,
      NodeTypes.IDENTIFIER has 1 incoming EdgeTypes.AST from SuperTypes.Expression or NodeTypes.CONTROL_STRUCTURE,
      NodeTypes.IDENTIFIER has 0 to N incoming EdgeTypes.CFG from SuperTypes.Expression or NodeTypes.METHOD,
      NodeTypes.IDENTIFIER has 0 to 1 incoming EdgeTypes.RECEIVER from NodeTypes.CALL,
      NodeTypes.IDENTIFIER has 0 to 1 incoming EdgeTypes.ARGUMENT from NodeTypes.CALL or NodeTypes.RETURN,
      NodeTypes.RETURN has 1 incoming EdgeTypes.AST from SuperTypes.Expression or NodeTypes.CONTROL_STRUCTURE,
      NodeTypes.RETURN has 0 to N incoming EdgeTypes.CFG from SuperTypes.Expression or NodeTypes.METHOD,
      NodeTypes.BLOCK has 1 incoming EdgeTypes.AST from SuperTypes.Expression or NodeTypes.METHOD or NodeTypes.CONTROL_STRUCTURE,
      NodeTypes.BLOCK has 0 to N incoming EdgeTypes.CFG from SuperTypes.Expression or NodeTypes.METHOD,
      NodeTypes.BLOCK has 0 to 1 incoming EdgeTypes.RECEIVER from NodeTypes.CALL,
      NodeTypes.BLOCK has 0 to 1 incoming EdgeTypes.ARGUMENT from NodeTypes.CALL or NodeTypes.RETURN,
      NodeTypes.METHOD_REF has 1 incoming EdgeTypes.AST from SuperTypes.Expression or NodeTypes.CONTROL_STRUCTURE,
      NodeTypes.METHOD_REF has 0 to N incoming EdgeTypes.CFG from SuperTypes.Expression or NodeTypes.METHOD,
      NodeTypes.METHOD_REF has 0 to 1 incoming EdgeTypes.RECEIVER from NodeTypes.CALL,
      NodeTypes.METHOD_REF has 0 to 1 incoming EdgeTypes.ARGUMENT from NodeTypes.CALL or NodeTypes.RETURN,
      NodeTypes.BINDING has 1 incoming EdgeTypes.BINDS from NodeTypes.TYPE_DECL,
      NodeTypes.FIELD_IDENTIFIER has 1 incoming EdgeTypes.ARGUMENT from NodeTypes.CALL,
      NodeTypes.FIELD_IDENTIFIER has 1 incoming EdgeTypes.AST from NodeTypes.CALL,
      NodeTypes.FIELD_IDENTIFIER has 1 incoming EdgeTypes.CFG from SuperTypes.Expression,
    )

}
