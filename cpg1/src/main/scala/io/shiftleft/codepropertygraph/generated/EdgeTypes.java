package io.shiftleft.codepropertygraph.generated;

import gremlin.scala.Key;

public class EdgeTypes {

/** Alias relation between types */
public static final String ALIAS_OF = "ALIAS_OF";

/** Syntax child */
public static final String AST = "AST";

/** Link between FRAMEWORK and FRAMEWORK_DATA nodes */
public static final String ATTACHED_DATA = "ATTACHED_DATA";

/** Type argument binding to a type parameter */
public static final String BINDS_TO = "BINDS_TO";

/** Referencing to e.g. a LOCAL */
public static final String CALL = "CALL";

/** Function call argument */
public static final String CALL_ARG = "CALL_ARG";

/** Function call output argument. Goes from METHOD_PARAMETER_OUT to call argument node */
public static final String CALL_ARG_OUT = "CALL_ARG_OUT";

/** Function call return value */
public static final String CALL_RET = "CALL_RET";

/** Represents the capturing of a variable into a closure */
public static final String CAPTURE = "CAPTURE";

/** Connection between a captured LOCAL and the corresponding CLOSURE_BINDING */
public static final String CAPTURED_BY = "CAPTURED_BY";

/** Control dependency graph */
public static final String CDG = "CDG";

/** Control flow */
public static final String CFG = "CFG";

/** Shortcut over multiple AST edges */
public static final String CONTAINS = "CONTAINS";

/** Membership relation for a compound object */
public static final String CONTAINS_NODE = "CONTAINS_NODE";

/** Points to dominated node in DOM tree */
public static final String DOMINATE = "DOMINATE";

/** Link to evaluation type */
public static final String EVAL_TYPE = "EVAL_TYPE";

/** Inheritance relation between types */
public static final String INHERITS_FROM = "INHERITS_FROM";

/**  */
public static final String IS_SENSITIVE_DATA_DESCR_OF = "IS_SENSITIVE_DATA_DESCR_OF";

/**  */
public static final String IS_SENSITIVE_DATA_DESCR_OF_REF = "IS_SENSITIVE_DATA_DESCR_OF_REF";

/**  */
public static final String IS_SENSITIVE_DATA_OF_TYPE = "IS_SENSITIVE_DATA_OF_TYPE";

/** Links together corresponding METHOD_PARAMETER_IN and METHOD_PARAMETER_OUT nodes */
public static final String PARAMETER_LINK = "PARAMETER_LINK";

/** Points to dominated node in post DOM tree */
public static final String POST_DOMINATE = "POST_DOMINATE";

/** Encodes propagation of data from on node to another */
public static final String PROPAGATE = "PROPAGATE";

/** Reaching definition edge */
public static final String REACHING_DEF = "REACHING_DEF";

/** The receiver of a method call which is either an object or a pointer */
public static final String RECEIVER = "RECEIVER";

/** A reference to e.g. a LOCAL */
public static final String REF = "REF";

/** Indicates the concrete TYPE for references aka IDENTIFIER or CALL(those which return references) nodes */
public static final String RESOLVED_TO = "RESOLVED_TO";

/** Edges from nodes to tags */
public static final String TAGGED_BY = "TAGGED_BY";

/** Indicates taint removal. Only present between corresponding METHOD_PARAMETER_IN and METHOD_PARAMETER_OUT nodes */
public static final String TAINT_REMOVE = "TAINT_REMOVE";

/** Indicates that a method is part of the vtable of a certain type declaration */
public static final String VTABLE = "VTABLE";


}
