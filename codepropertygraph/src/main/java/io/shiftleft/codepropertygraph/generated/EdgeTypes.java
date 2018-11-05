package io.shiftleft.codepropertygraph.generated;

import gremlin.scala.Key;

public class EdgeTypes {

/** Syntax child */
public static final String AST = "AST";

/** Type argument binding to a type parameter. */
public static final String BINDS_TO = "BINDS_TO";

/** Referencing to e.g. a LOCAL */
public static final String CALL = "CALL";

/** Function call argument */
public static final String CALL_ARG = "CALL_ARG";

/** Function call output argument. Goes from METHOD_PARAMETER_OUT to call argument node. */
public static final String CALL_ARG_OUT = "CALL_ARG_OUT";

/** Function call return value */
public static final String CALL_RET = "CALL_RET";

/** Represents the capturing of a variable into a closure. */
public static final String CAPTURE = "CAPTURE";

/** Connection between a capture LOCAL and the corresponding CLOSURE_BINDING */
public static final String CAPTURED_BY = "CAPTURED_BY";

/** Control flow */
public static final String CFG = "CFG";

/** Shortcut to contained node */
public static final String CONTAINS = "CONTAINS";

/** Link to evaluation type. */
public static final String EVAL_TYPE = "EVAL_TYPE";

/** Inheritance relation between types. */
public static final String INHERITS_FROM = "INHERITS_FROM";

/** Links together corresponding METHOD_PARAMETER_IN and METHOD_PARAMETER_OUT nodes. */
public static final String PARAMETER_LINK = "PARAMETER_LINK";

/** Referencing to e.g. a LOCAL */
public static final String REF = "REF";

/** Edges from nodes to tags */
public static final String TAGGED_BY = "TAGGED_BY";

/** Indicates that a method is part of the vtable of a certain type declaration. */
public static final String VTABLE = "VTABLE";


}
