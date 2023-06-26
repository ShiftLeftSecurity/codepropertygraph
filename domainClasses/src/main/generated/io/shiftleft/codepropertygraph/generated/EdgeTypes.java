package io.shiftleft.codepropertygraph.generated;

import overflowdb.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class EdgeTypes {

/** This edge represents an alias relation between a type declaration and a type.
The language frontend MUST NOT create `ALIAS_OF` edges as they are created
automatically based on `ALIAS_TYPE_FULL_NAME` fields when the CPG is first loaded. */
public static final String ALIAS_OF = "ALIAS_OF";

/** Argument edges connect call sites (node type `CALL`) to their arguments
(node type `EXPRESSION`) as well as `RETURN` nodes to the expressions
that return. */
public static final String ARGUMENT = "ARGUMENT";

/** This edge connects a parent node to its child in the syntax tree. */
public static final String AST = "AST";

/** This edge connects a type declaration (`TYPE_DECL`) with a binding node (`BINDING`) and
indicates that the type declaration has the binding represented by the binding node, in
other words, there is a (name, signature) pair that can be resolved for the type
declaration as stored in the binding node. */
public static final String BINDS = "BINDS";

/** This edge connects type arguments to type parameters to indicate
that the type argument is used to instantiate the type parameter. */
public static final String BINDS_TO = "BINDS_TO";

/** This edge connects call sites, i.e., nodes with the type `CALL`, to the
method node that represent the method they invoke. The frontend MAY create
`CALL` edges but is not required to do so. Instead, of the `METHOD_FULL_NAME`
field of the `CALL` node is set correctly, `CALL` edges are created
automatically as the CPG is first loaded. */
public static final String CALL = "CALL";

/** Represents the capturing of a variable into a closure */
public static final String CAPTURE = "CAPTURE";

/** Connection between a captured LOCAL and the corresponding CLOSURE_BINDING */
public static final String CAPTURED_BY = "CAPTURED_BY";

/** A CDG edge expresses that the destination node is control dependent on the source node. */
public static final String CDG = "CDG";

/** This edge indicates control flow from the source to the destination node. */
public static final String CFG = "CFG";

/** The edge connects control structure nodes to the expressions that holds their conditions. */
public static final String CONDITION = "CONDITION";

/** This edge connects a node to the method that contains it. */
public static final String CONTAINS = "CONTAINS";

/** This edge indicates that the source node immediately dominates the destination node. */
public static final String DOMINATE = "DOMINATE";

/** This edge connects a node to its evaluation type. */
public static final String EVAL_TYPE = "EVAL_TYPE";

/** Edge from imports to dependencies */
public static final String IMPORTS = "IMPORTS";

/** Inheritance relation between a type declaration and a type. This edge MUST NOT
 be created by the language frontend as it is automatically created from
 `INHERITS_FROM_TYPE_FULL_NAME` fields then the CPG is first loaded. */
public static final String INHERITS_FROM = "INHERITS_FROM";

/** Edge from CALL statement in the AST to the IMPORT.
￼        |We use this edge to traverse from the logical representation of the IMPORT
￼        |to the corresponding import statement in the AST.
￼        | */
public static final String IS_CALL_FOR_IMPORT = "IS_CALL_FOR_IMPORT";

/** This edge connects a method input parameter to the corresponding
method output parameter. */
public static final String PARAMETER_LINK = "PARAMETER_LINK";

/** This edge indicates that the source node immediately post dominates the destination node. */
public static final String POST_DOMINATE = "POST_DOMINATE";

/** A reaching definition edge indicates that a variable produced at the source node reaches
the destination node without being reassigned on the way. The `VARIABLE` property indicates
which variable is propagated. */
public static final String REACHING_DEF = "REACHING_DEF";

/** Similar to `ARGUMENT` edges, `RECEIVER` edges connect call sites
to their receiver arguments. A receiver argument is the object on
which a method operates, that is, it is the expression that is
assigned to the `this` pointer as control is transferred to the method. */
public static final String RECEIVER = "RECEIVER";

/** This edge indicates that the source node is an identifier that denotes
access to the destination node. For example, an identifier may reference
a local variable. */
public static final String REF = "REF";

/** This edge connects a node to the node that represents its source file. These
edges MUST not be created by the language frontend but are automatically
created based on `FILENAME` fields. */
public static final String SOURCE_FILE = "SOURCE_FILE";

/** Edges from nodes to the tags they are tagged by. */
public static final String TAGGED_BY = "TAGGED_BY";


public static Set<String> ALL = new HashSet<String>() {{
add(ALIAS_OF);
add(ARGUMENT);
add(AST);
add(BINDS);
add(BINDS_TO);
add(CALL);
add(CAPTURE);
add(CAPTURED_BY);
add(CDG);
add(CFG);
add(CONDITION);
add(CONTAINS);
add(DOMINATE);
add(EVAL_TYPE);
add(IMPORTS);
add(INHERITS_FROM);
add(IS_CALL_FOR_IMPORT);
add(PARAMETER_LINK);
add(POST_DOMINATE);
add(REACHING_DEF);
add(RECEIVER);
add(REF);
add(SOURCE_FILE);
add(TAGGED_BY);
}};

}