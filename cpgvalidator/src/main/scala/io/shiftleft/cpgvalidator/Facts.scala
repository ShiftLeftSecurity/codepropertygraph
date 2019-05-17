package io.shiftleft.cpgvalidator

import FactConstructionClasses._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}

object Facts {

  val nodeOutFacts = List(
    NodeTypes.FILE has 1 to N outgoing EdgeTypes.AST to NodeTypes.NAMESPACE_BLOCK,


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
    NodeTypes.TYPE_DECL has 0 to N outgoing EdgeTypes.VTABLE to NodeTypes.METHOD,


    NodeTypes.MEMBER has 0 to N outgoing EdgeTypes.AST to NodeTypes.ANNOTATION,
    NodeTypes.MEMBER has 0 to N outgoing EdgeTypes.AST to NodeTypes.MODIFIER,


    NodeTypes.LITERAL has 1 outgoing EdgeTypes.CFG to SuperTypes.Expression or NodeTypes.METHOD_RETURN,


    NodeTypes.CALL has 0 to N outgoing EdgeTypes.AST to SuperTypes.Expression,
    NodeTypes.CALL has 1 to N outgoing EdgeTypes.CFG to SuperTypes.Expression or NodeTypes.METHOD_RETURN,
    NodeTypes.CALL has 0 to 1 outgoing EdgeTypes.RECEIVER to
      NodeTypes.CALL or NodeTypes.IDENTIFIER or NodeTypes.LITERAL or NodeTypes.METHOD_REF or NodeTypes.BLOCK,


    NodeTypes.IDENTIFIER has 1 outgoing EdgeTypes.CFG to SuperTypes.Expression,
    NodeTypes.IDENTIFIER has 0 to 1 outgoing EdgeTypes.REF to NodeTypes.LOCAL,


    NodeTypes.RETURN has 0 to 1 outgoing EdgeTypes.AST to SuperTypes.Expression,
    NodeTypes.RETURN has 1 outgoing EdgeTypes.CFG to NodeTypes.METHOD_RETURN,


    NodeTypes.BLOCK has 0 to N outgoing EdgeTypes.AST to SuperTypes.Expression,
    NodeTypes.BLOCK has 0 to N outgoing EdgeTypes.AST to NodeTypes.LOCAL,
    NodeTypes.BLOCK has 0 to 1 outgoing EdgeTypes.CFG to SuperTypes.Expression or NodeTypes.METHOD_RETURN,


    NodeTypes.METHOD_INST has 0 to N outgoing EdgeTypes.AST to NodeTypes.TYPE_ARGUMENT,


    NodeTypes.METHOD_REF has 1 outgoing EdgeTypes.CFG to SuperTypes.Expression,
    NodeTypes.METHOD_REF has 1 to N outgoing EdgeTypes.CAPTURE to NodeTypes.CLOSURE_BINDING,


    NodeTypes.CLOSURE_BINDING has 1 outgoing EdgeTypes.REF to NodeTypes.LOCAL or NodeTypes.METHOD_PARAMETER_IN,
  )

  val nodeInFacts = List(
    NodeTypes.METHOD has 0 to N incoming EdgeTypes.VTABLE from NodeTypes.TYPE_DECL,


    NodeTypes.METHOD_PARAMETER_IN has 1 incoming EdgeTypes.AST from NodeTypes.METHOD,
    NodeTypes.METHOD_PARAMETER_IN has 0 to N incoming EdgeTypes.REF from NodeTypes.CLOSURE_BINDING,


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
