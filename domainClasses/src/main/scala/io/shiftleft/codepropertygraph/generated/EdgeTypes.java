package io.shiftleft.codepropertygraph.generated;

import overflowdb.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class EdgeTypes {

 /** Syntax tree edge */
 public static final String AST = "AST";

 /** Control flow edge */
 public static final String CFG = "CFG";

 /** Membership relation for a compound object. This edge is deprecated. */
 public static final String CONTAINS_NODE = "CONTAINS_NODE";

 /** Connection between a captured LOCAL and the corresponding CLOSURE_BINDING */
 public static final String CAPTURED_BY = "CAPTURED_BY";

 /** Type argument binding to a type parameter */
 public static final String BINDS_TO = "BINDS_TO";

 /** A reference to e.g. a LOCAL */
 public static final String REF = "REF";

 /** Indicates that a method is part of the vtable of a certain type declaration */
 public static final String VTABLE = "VTABLE";

 /** The receiver of a method call which is either an object or a pointer */
 public static final String RECEIVER = "RECEIVER";

 /** Edge from control structure node to the expression that holds the condition */
 public static final String CONDITION = "CONDITION";

 /** Relation between a CALL and its arguments and RETURN and the returned expression */
 public static final String ARGUMENT = "ARGUMENT";

 /** Source file of a node, in which its LINE_NUMBER and COLUMN_NUMBER are valid */
 public static final String SOURCE_FILE = "SOURCE_FILE";

 /** Links together corresponding METHOD_PARAMETER_IN and METHOD_PARAMETER_OUT nodes */
 public static final String PARAMETER_LINK = "PARAMETER_LINK";

 /** Referencing to e.g. a LOCAL */
 public static final String CALL = "CALL";

 /** Edges from nodes to tags */
 public static final String TAGGED_BY = "TAGGED_BY";

 /** Link to evaluation type */
 public static final String EVAL_TYPE = "EVAL_TYPE";

 /** Inheritance relation between types */
 public static final String INHERITS_FROM = "INHERITS_FROM";

 /** Shortcut over multiple AST edges */
 public static final String CONTAINS = "CONTAINS";

 /** Encodes propagation of data from on node to another. The ALIAS property is deprecated. */
 public static final String PROPAGATE = "PROPAGATE";

 /** Reaching definition edge */
 public static final String REACHING_DEF = "REACHING_DEF";

 /** Alias relation between types */
 public static final String ALIAS_OF = "ALIAS_OF";

 /** Alias relation between two TYPE_DECL */
 public static final String TYPE_DECL_ALIAS = "TYPE_DECL_ALIAS";

 /** Relation between TYPE_DECL and BINDING node */
 public static final String BINDS = "BINDS";

 /** Represents the capturing of a variable into a closure */
 public static final String CAPTURE = "CAPTURE";

 /** Indicates taint removal. Only present between corresponding METHOD_PARAMETER_IN and METHOD_PARAMETER_OUT nodes */
 public static final String TAINT_REMOVE = "TAINT_REMOVE";

 /** Indicates the dynamic type(s) of an entity. This comes initially from the frontend provided DYNAMIC_TYPE_HINT_FULL_NAME property and is extended by our type resolution */
 public static final String DYNAMIC_TYPE = "DYNAMIC_TYPE";

 /** Points to dominated node in DOM tree */
 public static final String DOMINATE = "DOMINATE";

 /** Points to dominated node in post DOM tree */
 public static final String POST_DOMINATE = "POST_DOMINATE";

 /** Control dependency graph */
 public static final String CDG = "CDG";

 /** Link between FRAMEWORK and FRAMEWORK_DATA nodes */
 public static final String ATTACHED_DATA = "ATTACHED_DATA";


 public static Set<String> ALL = new HashSet<String>() {{
     add(AST);
     add(CFG);
     add(CONTAINS_NODE);
     add(CAPTURED_BY);
     add(BINDS_TO);
     add(REF);
     add(VTABLE);
     add(RECEIVER);
     add(CONDITION);
     add(ARGUMENT);
     add(SOURCE_FILE);
     add(PARAMETER_LINK);
     add(CALL);
     add(TAGGED_BY);
     add(EVAL_TYPE);
     add(INHERITS_FROM);
     add(CONTAINS);
     add(PROPAGATE);
     add(REACHING_DEF);
     add(ALIAS_OF);
     add(TYPE_DECL_ALIAS);
     add(BINDS);
     add(CAPTURE);
     add(TAINT_REMOVE);
     add(DYNAMIC_TYPE);
     add(DOMINATE);
     add(POST_DOMINATE);
     add(CDG);
     add(ATTACHED_DATA);
 }};

}